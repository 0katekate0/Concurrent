package com.qg.service;

import com.qg.dto.ReturnResult;

/***
 * 本地用户业务接口
 */
public interface LocalUserService {

    public ReturnResult validateToken(String phone, String password) throws Exception;

    public ReturnResult removeToken(String token)throws Exception;

    public String createWxUserToken(String userInfoJsonstr)throws Exception;

}
