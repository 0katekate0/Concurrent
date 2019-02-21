package com.qg.controller;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.CommonException;
import com.qg.pojo.QgUser;
import com.qg.service.LocalUserService;
import com.qg.service.QgUserService;
import com.qg.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/***
 * 用户控制器
 */
@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private LocalUserService localUserService;
    /***
     * 用户登录的方法
     * @param phone
     * @param password
     * @return
     */
    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult doLogin(String phone, String password, HttpServletResponse response)throws Exception{
        return localUserService.validateToken(phone,password);
    }
    /***
     * 用户注销的方法
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v/loginOut",method = RequestMethod.POST)
    @ResponseBody
    public ReturnResult loginOut(String token)throws Exception{
        return localUserService.removeToken(token);
    }
}
