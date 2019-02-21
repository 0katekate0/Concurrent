package com.qg.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.qg.config.AlipayConfig;
import com.qg.pojo.QgGoods;
import com.qg.pojo.QgOrder;
import com.qg.service.LocalPayService;
import com.qg.service.QgGoodsService;
import com.qg.service.QgOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class AliPayController {


    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private LocalPayService localPayService;

    /***
     * 去支付接口
     * @param orderId
     */
    @RequestMapping("/v/toPay")
    public void toPay(String orderId,HttpServletResponse response) throws Exception {
        String result = localPayService.createAliForm(orderId);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(result);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /***
     * 回调
     */
   @RequestMapping("/callBack")
   public String callBack(HttpServletRequest request) throws Exception {
       // 验证签名的return url
       boolean signVerified=localPayService.validateAliPay(request.getParameterMap());
       String orderId=null;
       // 验证成功
       if(signVerified) {
           // 商户订单号
           String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
           // 支付宝交易号
           String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
           // 验证成功，保存交易记录、修改库存状态
           orderId = localPayService.dealPaySuccess(out_trade_no, trade_no);
           return "redirect:" + alipayConfig.paySuccessUrl+"?orderId="+orderId;
       }else {
           // 验证失败
           return "redirect:" + alipayConfig.payFailUrl+"?orderId="+orderId;
       }
   }

    /***
     * 异步通知
     */
   @RequestMapping("/payNotify")
   public void payNotify(HttpServletRequest request,HttpServletResponse response) throws Exception {
       boolean signVerified=localPayService.validateAliPay(request.getParameterMap());
       if(signVerified) {//验证成功
           //商户订单号
           String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
           //支付宝交易号
           String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
           //查询交易记录表看是否执行了业务操作
           boolean flag=localPayService.validateDealPaySuccess(trade_no);
           if(!flag){
              localPayService.dealPaySuccess(out_trade_no,trade_no);
           }
           response.getWriter().println("success");
       }else {//验证失败
           response.getWriter().println("fail");
       }
       response.getWriter().flush();
       response.getWriter().close();
   }
}
