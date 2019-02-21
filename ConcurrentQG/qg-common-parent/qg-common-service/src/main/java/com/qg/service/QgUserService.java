package com.qg.service;
import com.qg.pojo.QgUser;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface QgUserService {

    public QgUser getQgUserById(String id)throws Exception;

    public List<QgUser>	getQgUserListByMap(Map<String,Object> param)throws Exception;

    public Integer getQgUserCountByMap(Map<String,Object> param)throws Exception;

    public Integer qdtxAddQgUser(QgUser qgUser)throws Exception;

    public Integer qdtxModifyQgUser(QgUser qgUser)throws Exception;

    public Integer qdtxDeleteQgUserById(String id)throws Exception;

    public Integer qdtxBatchDeleteQgUser(String ids)throws Exception;

    /***
     * 根据手机号和密码来查询用户信息
     * @return
     * @throws Exception
     */
    public QgUser queryQgUserByPhoneAndPwd(String phone,String pwd)throws Exception;

}
