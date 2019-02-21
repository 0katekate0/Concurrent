package com.qg.service.impl;
import com.qg.mapper.QgUserMapper;
import com.qg.pojo.QgUser;
import com.qg.service.QgUserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Service(interfaceClass=QgUserService.class)
public class QgUserServiceImpl implements QgUserService {

    @Resource
    private QgUserMapper qgUserMapper;

    public QgUser getQgUserById(String id)throws Exception{
        return qgUserMapper.getQgUserById(id);
    }

    public List<QgUser>	getQgUserListByMap(Map<String,Object> param)throws Exception{
        return qgUserMapper.getQgUserListByMap(param);
    }

    public Integer getQgUserCountByMap(Map<String,Object> param)throws Exception{
        return qgUserMapper.getQgUserCountByMap(param);
    }

    public Integer qdtxAddQgUser(QgUser qgUser)throws Exception{
            //qgUser.setCreatedTime(new Date());
            return qgUserMapper.insertQgUser(qgUser);
    }

    public Integer qdtxModifyQgUser(QgUser qgUser)throws Exception{
        //qgUser.setUpdatedTime(new Date());
        return qgUserMapper.updateQgUser(qgUser);
    }

    public Integer qdtxDeleteQgUserById(String id)throws Exception{
        return qgUserMapper.deleteQgUserById(id);
    }

    public Integer qdtxBatchDeleteQgUser(String ids)throws Exception{
        Map<String,List<String>> param=new HashMap<String,List<String>>();
        String[] paramArrays=ids.split(",");
        List<String> idList=new ArrayList<String>();
            for (String temp:paramArrays){
                idList.add(temp);
            }
        param.put("ids",idList);
        return qgUserMapper.batchDeleteQgUser(param);
    }

    @Override
    public QgUser queryQgUserByPhoneAndPwd(String phone,String pwd) throws Exception {
        Map<String,String> param=new HashMap<String, String>();
        param.put("phone",phone);
        param.put("password",pwd);
        return qgUserMapper.queryQgUserByPhoneAndPwd(param);
    }
}
