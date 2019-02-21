package com.qg.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.qg.common.Constants;
import com.qg.dto.ReturnResult;
import com.qg.dto.ReturnResultUtils;
import com.qg.exception.CommonException;
import com.qg.exception.UserException;
import com.qg.pojo.QgUser;
import com.qg.service.LocalUserService;
import com.qg.service.QgUserService;
import com.qg.utils.EmptyUtils;
import com.qg.utils.IdWorker;
import com.qg.utils.RedisUtil;
import com.qg.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 本地用户的业务处理
 */
@Component
public class LocalUserServiceImpl implements LocalUserService {

    @Reference
    private QgUserService qgUserService;

    @Autowired
    private RedisUtil redisUtil;

    /***
     * 根据用户名和密码生成token
     * @param phone
     * @param password
     * @return
     */
    public ReturnResult validateToken(String phone, String password)throws Exception{
        ReturnResult returnResult=null;
        QgUser qgUser=null;
        String token=null;
        qgUser=qgUserService.queryQgUserByPhoneAndPwd(phone,password);
        if(null!=qgUser){
            //如果验证成功，生成token放在redis
            String oldToken=redisUtil.getStr(qgUser.getId());
            if(EmptyUtils.isNotEmpty(oldToken)){
                redisUtil.del(oldToken);
                redisUtil.del(qgUser.getId());
            }
            token=Constants.tokenPrefix+TokenUtils.createToken(qgUser.getId(),qgUser.getPhone());
            redisUtil.setStr(token, JSONObject.toJSONString(qgUser), Constants.loginExpire);
            redisUtil.setStr(qgUser.getId(), token,Constants.loginExpire);
            Map<String,Object> result=new HashMap<String,Object>();
            result.put("token",token);
            returnResult= ReturnResultUtils.returnSuccess(result);
        }else{
            returnResult= ReturnResultUtils.returnFail(UserException.USER_PASSWORD_ERROR.getCode(),UserException.USER_PASSWORD_ERROR.getMessage());
        }
        return returnResult;
    }

    @Override
    public ReturnResult removeToken(String token) throws Exception {
        String qgUserJson=redisUtil.getStr(token);
        QgUser qgUser=JSONObject.parseObject(qgUserJson,QgUser.class);
        redisUtil.del(token);
        redisUtil.del(qgUser.getId());
        return  ReturnResultUtils.returnSuccess();
    }

    @Override
    public String createWxUserToken(String userInfoJsonstr) throws Exception {
        JSONObject userInfoJson=JSONObject.parseObject(userInfoJsonstr);
        String openId=userInfoJson.getString("openid");
        Map<String,Object> param=new HashMap<String,Object>();
        List<QgUser> qgUserList=qgUserService.getQgUserListByMap(param);
        QgUser qgUser=null;
        if(EmptyUtils.isEmpty(qgUserList)){ //如果不在库中，说明用户首次进行微信登录
            qgUser=new QgUser();
            qgUser.setId(IdWorker.getId());
            qgUser.setCreatedTime(new Date());
            qgUser.setRealName(userInfoJson.getString("nickname"));
            qgUser.setUpdatedTime(new Date());
            qgUser.setWxUserId(openId);
            qgUserService.qdtxAddQgUser(qgUser);
        }else{
            qgUser=qgUserList.get(0);
        }
        //为用户执行自动登录的操作
        String token= Constants.tokenPrefix+ TokenUtils.createToken(qgUser.getId(),qgUser.getWxUserId());
        redisUtil.setStr(token, JSONObject.toJSONString(qgUser), Constants.loginExpire);
        redisUtil.setStr(qgUser.getId(), token,Constants.loginExpire);
        return token;
    }
}
