package com.qg.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qg.common.Constants;
import com.qg.config.AlipayConfig;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgGoodsTempStock;
import com.qg.pojo.QgOrder;
import com.qg.pojo.QgTrade;
import com.qg.service.*;
import com.qg.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class LocalPayServiceImpl implements LocalPayService{

    @Autowired
    private AlipayConfig alipayConfig;

    @Reference
    private QgOrderService qgOrderService;

    @Reference
    private QgGoodsService qgGoodsService;

    @Reference
    private QgTradeService qgTradeService;

    @Reference
    private QgGoodsTempStockService qgGoodsTempStockService;

    /**
     * 第一步alipay 请求数据的form表单
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public String createAliForm(String orderId) throws Exception {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
        // 根据orderId查询到订单数据和商品信息
        QgOrder qgOrder = qgOrderService.getQgOrderById(orderId);
        QgGoods qgGoods = qgGoodsService.getQgGoodsById(qgOrder.getGoodsId());
        // json：订单号、订单金额、商品名称、描述
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ qgOrder.getOrderNo() +"\","
                + "\"total_amount\":\""+ qgOrder.getAmount() +"\","
                + "\"subject\":\""+ qgGoods.getGoodsName() +"\","
                + "\"body\":\""+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 返回请求的表单
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    /**
     * 验证签名return url
     * @param requestParams
     * @return
     * @throws Exception
     */
    @Override
    public boolean validateAliPay(Map<String,String[]> requestParams) throws Exception {
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 调用SDK验证签名
        return AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
    }

    /**
     * 验证成功，保存交易记录、修改库存状态
     * @param orderNo
     * @param tradeNo
     * @return
     * @throws Exception
     */
    @Override
    public String dealPaySuccess(String orderNo, String tradeNo) throws Exception {
        // 根据订单号查出订单信息
        QgOrder qgOrder = qgOrderService.queryQgOrderByNo(orderNo);
        // 1.保存交易记录，交易金额、订单号、交易编号
        saveTrade(qgOrder.getAmount(), orderNo, tradeNo);
        // 2.修改订单状态
        updateOrder(qgOrder);
        // 3.修改库存状态
        updateStock(qgOrder.getStockId());
        return qgOrder.getId();
    }

    @Override
    public boolean validateDealPaySuccess(String tradeNo) throws Exception {
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("tradeNo",tradeNo);
        Integer count=qgTradeService.getQgTradeCountByMap(param);
        return count>0;
    }

    /**
     * 保存交易记录，交易金额、订单号、交易编号
     * @param amount
     * @param orderNo
     * @param tradeNo
     * @throws Exception
     */
    private void saveTrade(double amount,String orderNo,String tradeNo) throws Exception {
        QgTrade qgTrade = new QgTrade();
        qgTrade.setId(IdWorker.getId());
        qgTrade.setAmount(amount);
        qgTrade.setCreatedTime(new Date());
        qgTrade.setOrderNo(orderNo);
        qgTrade.setPayMethod(Constants.PayMethod.aliPay);
        qgTrade.setTradeNo(tradeNo);
        qgTrade.setUpdatedTime(new Date());
        qgTradeService.qdtxAddQgTrade(qgTrade);
    }

    /**
     * 修改订单状态
     * @param qgOrder
     * @throws Exception
     */
    private void updateOrder(QgOrder qgOrder)throws Exception{
        qgOrder.setStatus(Constants.OrderStatus.paySuccess);
        qgOrder.setUpdatedTime(new Date());
        qgOrderService.qdtxModifyQgOrder(qgOrder);
    }

    /**
     * 修改库存状态
     * @param stockId
     * @throws Exception
     */
    private void updateStock(String stockId)throws Exception{
        QgGoodsTempStock qgGoodsTempStock=qgGoodsTempStockService.getQgGoodsTempStockById(stockId);
        qgGoodsTempStock.setStatus(Constants.StockStatus.paySuccess);
        qgGoodsTempStock.setUpdatedTime(new Date());
        qgGoodsTempStockService.qdtxModifyQgGoodsTempStock(qgGoodsTempStock);
    }
}
