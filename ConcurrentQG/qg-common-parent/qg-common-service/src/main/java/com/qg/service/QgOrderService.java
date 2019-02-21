package com.qg.service;
import com.qg.pojo.QgOrder;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface QgOrderService {

    public QgOrder getQgOrderById(String id)throws Exception;

    public List<QgOrder>	getQgOrderListByMap(Map<String,Object> param)throws Exception;

    public Integer getQgOrderCountByMap(Map<String,Object> param)throws Exception;

    public Integer qdtxAddQgOrder(QgOrder qgOrder)throws Exception;

    public Integer qdtxModifyQgOrder(QgOrder qgOrder)throws Exception;

    public Integer qdtxDeleteQgOrderById(String id)throws Exception;

    public Integer qdtxBatchDeleteQgOrder(String ids)throws Exception;

    public QgOrder queryQgOrderByNo(String orderNo)throws Exception;

}
