package com.qg.service;

import java.util.Map;

/***
 * 支付本地业务
 */
public interface LocalPayService {

    public String createAliForm(String orderId)throws Exception;

    public boolean validateAliPay(Map<String,String[]> requestParams)throws Exception;

    public String dealPaySuccess(String orderNo,String tradeNo) throws Exception;

    public boolean validateDealPaySuccess(String tradeNo)throws Exception;
}
