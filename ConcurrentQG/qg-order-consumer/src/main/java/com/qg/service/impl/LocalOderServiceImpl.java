package com.qg.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.qg.common.Constants;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.OrderException;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgOrder;
import com.qg.pojo.QgUser;
import com.qg.service.LocalOderService;
import com.qg.service.QgGoodsService;
import com.qg.service.QgOrderService;
import com.qg.utils.EmptyUtils;
import com.qg.utils.RedisUtil;
import com.qg.vo.GoodsVo;
import com.qg.vo.OrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LocalOderServiceImpl implements LocalOderService{

    @Reference
    private QgOrderService qgOrderService;

    @Reference
    private QgGoodsService qgGoodsService;

    @Autowired
    private RedisUtil redisUtil;

    /***
     * 查询订单的业务方法
     * @return
     * @throws Exception
     */
    public ReturnResult<List<OrderVo>> queryOrderList(String token)throws Exception{
        List<OrderVo> orderVoList=null;
        // 通过token来拿用户信息
        String userJson = redisUtil.getStr(token);
        QgUser qgUser = JSONObject.parseObject(userJson,QgUser.class);

        Map<String,Object> param = new HashMap<String,Object>();
        param.put("userId", qgUser.getId());
        // 1.根据用户信息查询订单
        List<QgOrder> qgOrderList=qgOrderService.getQgOrderListByMap(param);

        // 2.组合OrderVo
        if(EmptyUtils.isNotEmpty(qgOrderList)){
            orderVoList = new ArrayList<OrderVo>();
            for (QgOrder qgOrder:qgOrderList){
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(qgOrder, orderVo);

                // 避免多次查询数据库，先获取redis里面的goods信息
                String goodsVoJson = redisUtil.getStr(Constants.goodsPrefix+orderVo.getGoodsId());
                // 如果信息为空查数据库
                if(EmptyUtils.isEmpty(goodsVoJson)){
                    // 根据goodsid查出商品再赋值商品图片
                    QgGoods qgGoods = qgGoodsService.getQgGoodsById(orderVo.getGoodsId());
                    // 设置商品图片
                    orderVo.setGoodsImg(qgGoods.getGoodsImg());
                }else{
                    // 信息不为空，从redis里面取图片
                    GoodsVo goodsVo=JSONObject.parseObject(goodsVoJson, GoodsVo.class);
                    orderVo.setGoodsImg(goodsVo.getGoodsImg());
                }
                // 生成一个订单来装一个订单orderVo
                orderVoList.add(orderVo);
            }
            return ReturnResultUtils.returnSuccess(orderVoList);
        }
        // 否则返回订单list为空
        return ReturnResultUtils.returnSuccess(null);
    }

    /***
     * 根据订单id查询订单信息（订单编号和订单金额）
     */
    public ReturnResult<QgOrder> queryOrderById(String orderId,String token)throws Exception{
        // 获取订单信息
        QgOrder qgOrder = qgOrderService.getQgOrderById(orderId);
        // 获取用户信息
        String userJson = redisUtil.getStr(token);
        QgUser qgUser= JSONObject.parseObject(userJson, QgUser.class);
        // 如果没有查询到订单或者非法查询（查询别人的订单）则返回没找到订单
        if(EmptyUtils.isEmpty(qgOrder) || !qgOrder.getUserId().equals(qgUser.getId())){
            return ReturnResultUtils.returnFail(OrderException.ORDER_NOT_EXIST.getCode(),OrderException.ORDER_NOT_EXIST.getMessage());
        }
        // 否则就是成功
        return ReturnResultUtils.returnSuccess(qgOrder);
    }

}
