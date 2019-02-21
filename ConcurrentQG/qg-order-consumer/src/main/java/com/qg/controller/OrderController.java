package com.qg.controller;

import com.qg.dto.ReturnResult;
import com.qg.service.LocalOderService;
import com.qg.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private LocalOderService localOderService;

    /***
     * 查询订单list信息
     * @return
     */
    @RequestMapping("/v/queryOrderList")
    @ResponseBody
    public ReturnResult<List<OrderVo>> queryOrderList(String token) throws Exception {
        return localOderService.queryOrderList(token);
    }

    /**
     * 根据id查询订单
     * @param orderId
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping("/v/prepay")
    @ResponseBody
    public ReturnResult prepay(String orderId,String token) throws Exception {
        return localOderService.queryOrderById(orderId,token);
    }
}
