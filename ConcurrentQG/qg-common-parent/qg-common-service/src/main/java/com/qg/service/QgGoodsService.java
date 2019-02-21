package com.qg.service;
import com.qg.pojo.QgGoods;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
* Created by shang-pc on 2015/11/7.
*/
public interface QgGoodsService {

    public QgGoods getQgGoodsById(String id)throws Exception;

    public List<QgGoods>	getQgGoodsListByMap(Map<String,Object> param)throws Exception;

    public Integer getQgGoodsCountByMap(Map<String,Object> param)throws Exception;

    public Integer qdtxAddQgGoods(QgGoods qgGoods)throws Exception;

    public Integer qdtxModifyQgGoods(QgGoods qgGoods)throws Exception;

    public Integer qdtxDeleteQgGoodsById(String id)throws Exception;

    public Integer qdtxBatchDeleteQgGoods(String ids)throws Exception;

}
