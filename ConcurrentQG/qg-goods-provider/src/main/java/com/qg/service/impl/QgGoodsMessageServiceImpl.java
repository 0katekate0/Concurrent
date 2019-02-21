package com.qg.service.impl;
import com.qg.mapper.QgGoodsMessageMapper;
import com.qg.pojo.QgGoodsMessage;
import com.qg.service.QgGoodsMessageService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Service(interfaceClass=QgGoodsMessageService.class)
public class QgGoodsMessageServiceImpl implements QgGoodsMessageService {

    @Resource
    private QgGoodsMessageMapper qgGoodsMessageMapper;

    public QgGoodsMessage getQgGoodsMessageById(String id)throws Exception{
        return qgGoodsMessageMapper.getQgGoodsMessageById(id);
    }

    public List<QgGoodsMessage>	getQgGoodsMessageListByMap(Map<String,Object> param)throws Exception{
        return qgGoodsMessageMapper.getQgGoodsMessageListByMap(param);
    }

    public Integer getQgGoodsMessageCountByMap(Map<String,Object> param)throws Exception{
        return qgGoodsMessageMapper.getQgGoodsMessageCountByMap(param);
    }

    public Integer qdtxAddQgGoodsMessage(QgGoodsMessage qgGoodsMessage)throws Exception{
            //qgGoodsMessage.setCreatedTime(new Date());
            return qgGoodsMessageMapper.insertQgGoodsMessage(qgGoodsMessage);
    }

    public Integer qdtxModifyQgGoodsMessage(QgGoodsMessage qgGoodsMessage)throws Exception{
        //qgGoodsMessage.setUpdatedTime(new Date());
        return qgGoodsMessageMapper.updateQgGoodsMessage(qgGoodsMessage);
    }

    public Integer qdtxDeleteQgGoodsMessageById(String id)throws Exception{
        return qgGoodsMessageMapper.deleteQgGoodsMessageById(id);
    }

    public Integer qdtxBatchDeleteQgGoodsMessage(String ids)throws Exception{
        Map<String,List<String>> param=new HashMap<String,List<String>>();
        String[] paramArrays=ids.split(",");
        List<String> idList=new ArrayList<String>();
            for (String temp:paramArrays){
                idList.add(temp);
            }
        param.put("ids",idList);
        return qgGoodsMessageMapper.batchDeleteQgGoodsMessage(param);
    }


}
