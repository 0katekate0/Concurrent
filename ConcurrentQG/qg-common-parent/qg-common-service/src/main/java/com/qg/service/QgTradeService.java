package com.qg.service;
import com.qg.pojo.QgTrade;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface QgTradeService {

    public QgTrade getQgTradeById(String id)throws Exception;

    public List<QgTrade>	getQgTradeListByMap(Map<String,Object> param)throws Exception;

    public Integer getQgTradeCountByMap(Map<String,Object> param)throws Exception;

    public Integer qdtxAddQgTrade(QgTrade qgTrade)throws Exception;

    public Integer qdtxModifyQgTrade(QgTrade qgTrade)throws Exception;

    public Integer qdtxDeleteQgTradeById(String id)throws Exception;

    public Integer qdtxBatchDeleteQgTrade(String ids)throws Exception;

}
