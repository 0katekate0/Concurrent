package com.qg.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.qg.common.Constants;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.GoodsException;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgGoodsTempStock;
import com.qg.pojo.QgOrder;
import com.qg.pojo.QgUser;
import com.qg.utils.ActiveMQUtils;
import com.qg.utils.EmptyUtils;
import com.qg.utils.IdWorker;
import com.qg.utils.RedisUtil;
import com.qg.vo.GetGoodsMessage;
import com.qg.vo.GoodsVo;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class LocalGoodsServiceImpl implements LocalGoodsService{

    @Reference
    private QgGoodsService qgGoodsService;

    @Reference
    private QgGoodsTempStockService qgGoodsTempStockService;

    @Reference
    private QgOrderService qgOrderService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ActiveMQUtils activeMQUtils;

    @Override
    public ReturnResult queryGoodsById(String id) throws Exception {
        GoodsVo goodsVo=null;
        //1.首先从redis中进行获取
        //2.redis中没有，则走数据库查询，并将结果写入到redis
        //3.redis中有，则走redis
        String goodsVoJson=redisUtil.getStr(Constants.goodsPrefix+id);
        if(EmptyUtils.isEmpty(goodsVoJson)){
            goodsVo=new GoodsVo();
            QgGoods qgGoods=qgGoodsService.getQgGoodsById(id);
            BeanUtils.copyProperties(qgGoods,goodsVo);
            //获取库存信息
            //1.获取临时库存表中，goods_id 为id的有效的记录数->用户已消费或待消费记录数 100 activeCount
            Map<String,Object> param=new HashMap<String,Object>();
            param.put("goodsId",id);
            param.put("active",1);
            Integer activeCount=qgGoodsTempStockService.getQgGoodsTempStockCountByMap(param);
            //实际库存
            Integer currentCount=goodsVo.getStock()-activeCount;
            goodsVo.setCurrentStock(currentCount);
            //放在redis当中
            redisUtil.setStr(Constants.goodsPrefix+id, JSONObject.toJSONString(goodsVo));
        }else{
            goodsVo=JSONObject.parseObject(goodsVoJson,GoodsVo.class);
        }
        return ReturnResultUtils.returnSuccess(goodsVo);
    }

    /***
     * 处理抢购请求的方法
     *
     * 只需要从mq里面获取消息，更新redis就行，不需要有返回值
     * @param getGoodsMessage
     * @throws Exception
     */
    @JmsListener(destination = Constants.ActiveMQMessage.getMessage)
    private void getGoods(GetGoodsMessage getGoodsMessage) throws Exception {
        String userId = getGoodsMessage.getUserId();
        String goodsId = getGoodsMessage.getGoodsId();
        // 1.查看用户是否已经抢购过该商品，如果用户有抢购成功未支付的或已经支付成功的记录，则不能抢

        // 获取锁，如果没有获取到锁，就等待3s，获取到锁的线程就继续执行
        while(!redisUtil.lock(Constants.lockPrefix + goodsId, Constants.lockExpire)){
            Thread.sleep(3);
        }

        // 获取到锁继续往下执行：
        String getFlag = redisUtil.getStr(Constants.getGoodsPrefix + goodsId + ":" + userId);
        // 判断用户是否已经抢购成功，如果已经抢购过，则跳过
        // 不为空并且只有一个，说明已经抢购过
        if(EmptyUtils.isNotEmpty(getFlag) && getFlag.equals(Constants.GetGoodsStatus.getSuccess)){
            // 释放锁
            redisUtil.unLock(Constants.lockPrefix+goodsId);
            return;
            //return ReturnResultUtils.returnFail(GoodsException.USER_REPEAT_GET.getCode(),GoodsException.USER_REPEAT_GET.getMessage());
        }

        // 如果用户没有抢购过，往下执行
        // 2.判断库存是否大于0,如果大于0则进入抢购环节
        String goodsVoJson=redisUtil.getStr(Constants.goodsPrefix + goodsId);
        GoodsVo goodsVo=JSONObject.parseObject(goodsVoJson,GoodsVo.class);
        if(goodsVo.getCurrentStock() <= 0){
            // 返回需要释放掉锁
            redisUtil.unLock(Constants.lockPrefix+goodsId);
            // 写入失败状态到redis当中，轮询接口轮询到数据为0的时候，代表用户没抢到，返回前端数据，否则会一直等待
            redisUtil.setStr(Constants.getGoodsPrefix + goodsId +":"+ userId, Constants.GetGoodsStatus.getFail);
            return;
            //return ReturnResultUtils.returnFail(GoodsException.GOODS_IS_CLEAR.getCode(),GoodsException.GOODS_IS_CLEAR.getMessage());
        }

        // 3.更新Redis库存
        goodsVo.setCurrentStock(goodsVo.getCurrentStock()-1);//当前库存减一
        redisUtil.setStr(Constants.goodsPrefix + goodsId, JSONObject.toJSONString(goodsVo));

        // 4.记录用户购买库存数据，返回stockId因为第5步，生成订单的时候需要这个库存id
        String stockId = saveQgGoodsTempStock(userId, goodsId);

        // 5.生成订单
        saveOrder(stockId, goodsVo.getId(), userId, goodsVo.getPrice());

        // 6.如果抢购成功，写入redis成功的状态，在redis中，增加用户已抢购到商品的标识，抢购到了之后放到redis就是1：getGoods:+goodsId:+userId 1
        redisUtil.setStr(Constants.getGoodsPrefix+goodsId+":"+userId, Constants.GetGoodsStatus.getSuccess);
        // 返回结果之前释放锁
        redisUtil.unLock(Constants.lockPrefix + goodsId);

        // 7.返回执行结果
        //return ReturnResultUtils.returnSuccess();
    }

    private void saveOrder(String stockId, String goodsId, String userId, double amount) throws Exception {
        QgOrder qgOrder=new QgOrder();
        qgOrder.setId(IdWorker.getId());
        qgOrder.setCreatedTime(new Date());
        qgOrder.setGoodsId(goodsId);
        // 待支付
        qgOrder.setStatus(Constants.OrderStatus.toPay);
        qgOrder.setUpdatedTime(new Date());
        qgOrder.setUserId(userId);
        qgOrder.setAmount(amount);
        qgOrder.setNum(1);
        qgOrder.setOrderNo(IdWorker.getId());
        qgOrder.setStockId(stockId);
        // 保存
        qgOrderService.qdtxAddQgOrder(qgOrder);
    }

    /**
     * 商品抢购发送到mq消息
     * @param token
     * @param goodsId
     * @return
     * @throws Exception
     */
    @Override
    public ReturnResult goodsGetMessage(String token, String goodsId) throws Exception {
        // 将用户id和抢购的商品id，写入到消息中间件
        // 根据token知道是哪个用户，获取用户信息
        String userJson = redisUtil.getStr(token);
        QgUser qgUser = JSONObject.parseObject(userJson,QgUser.class);
        GetGoodsMessage getGoodsMessage=new GetGoodsMessage();
        getGoodsMessage.setUserId(qgUser.getId());
        getGoodsMessage.setGoodsId(goodsId);
        activeMQUtils.sendQueueMesage(Constants.ActiveMQMessage.getMessage, getGoodsMessage);
        return ReturnResultUtils.returnSuccess();
    }

    /***
     * 保存抢购信息到临时库存记录表
     * @param userId
     * @param goodsId
     * @throws Exception
     */
    private String saveQgGoodsTempStock(String userId,String goodsId) throws Exception {
        QgGoodsTempStock qgGoodsTempStock=new QgGoodsTempStock();
        // 生成库存id
        qgGoodsTempStock.setId(IdWorker.getId());
        qgGoodsTempStock.setUserId(userId);
        qgGoodsTempStock.setCreatedTime(new Date());
        qgGoodsTempStock.setGoodsId(goodsId);
        qgGoodsTempStock.setStatus(Constants.StockStatus.lock);
        qgGoodsTempStock.setUpdatedTime(new Date());
        qgGoodsTempStockService.qdtxAddQgGoodsTempStock(qgGoodsTempStock);
        // 返回库存id,因为生成订单的时候会用到
        return qgGoodsTempStock.getId();
    }

    /***
     * 在redis中查询用户的抢购状态，返回给前端信息
     * @param token
     * @param goodsId
     * @return
     * @throws Exception
     */
    @Override
    public ReturnResult flushGetGoodsStatus(String token, String goodsId) throws Exception {
        // 1.获取用户信息
        String userJson=redisUtil.getStr(token);
        QgUser qgUser = JSONObject.parseObject(userJson,QgUser.class);

        // 2.根据用户信息和商品信息，查询抢购的状态，轮询拿到的数据给前端
        String getFlag = redisUtil.getStr(Constants.getGoodsPrefix+goodsId+":"+qgUser.getId());
        if(EmptyUtils.isEmpty(getFlag)){
            // 说明还没抢到，正在排队抢购
            return ReturnResultUtils.returnFail(GoodsException.GOODS_IS_GETTING.getCode(), GoodsException.GOODS_IS_GETTING.getMessage());
        }else if(getFlag.equals("0")){
            // 抢购失败，redis里面设置失败为0，库存没有了
            return ReturnResultUtils.returnFail(GoodsException.GOODS_IS_CLEAR.getCode(), GoodsException.GOODS_IS_CLEAR.getMessage());
        }else{
            // 抢购成功的
            return ReturnResultUtils.returnSuccess();
        }
    }
}
