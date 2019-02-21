package com.qg.service;
import com.qg.pojo.QgGoodsMessage;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface QgGoodsMessageService {

    public QgGoodsMessage getQgGoodsMessageById(String id)throws Exception;

    public List<QgGoodsMessage>	getQgGoodsMessageListByMap(Map<String,Object> param)throws Exception;

    public Integer getQgGoodsMessageCountByMap(Map<String,Object> param)throws Exception;

    public Integer qdtxAddQgGoodsMessage(QgGoodsMessage qgGoodsMessage)throws Exception;

    public Integer qdtxModifyQgGoodsMessage(QgGoodsMessage qgGoodsMessage)throws Exception;

    public Integer qdtxDeleteQgGoodsMessageById(String id)throws Exception;

    public Integer qdtxBatchDeleteQgGoodsMessage(String ids)throws Exception;

}
