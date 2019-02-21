package com.qg.service;
import com.qg.pojo.QgGoodsTempStock;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface QgGoodsTempStockService {

    public QgGoodsTempStock getQgGoodsTempStockById(String id)throws Exception;

    public List<QgGoodsTempStock>	getQgGoodsTempStockListByMap(Map<String,Object> param)throws Exception;

    public Integer getQgGoodsTempStockCountByMap(Map<String,Object> param)throws Exception;

    public Integer qdtxAddQgGoodsTempStock(QgGoodsTempStock qgGoodsTempStock)throws Exception;

    public Integer qdtxModifyQgGoodsTempStock(QgGoodsTempStock qgGoodsTempStock)throws Exception;

    public Integer qdtxDeleteQgGoodsTempStockById(String id)throws Exception;

    public Integer qdtxBatchDeleteQgGoodsTempStock(String ids)throws Exception;

}
