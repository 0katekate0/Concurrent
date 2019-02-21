package com.qg.service.impl;
import com.qg.mapper.QgGoodsTempStockMapper;
import com.qg.pojo.QgGoodsTempStock;
import com.qg.service.QgGoodsTempStockService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Service(interfaceClass=QgGoodsTempStockService.class)
public class QgGoodsTempStockServiceImpl implements QgGoodsTempStockService {

    @Resource
    private QgGoodsTempStockMapper qgGoodsTempStockMapper;

    public QgGoodsTempStock getQgGoodsTempStockById(String id)throws Exception{
        return qgGoodsTempStockMapper.getQgGoodsTempStockById(id);
    }

    public List<QgGoodsTempStock>	getQgGoodsTempStockListByMap(Map<String,Object> param)throws Exception{
        return qgGoodsTempStockMapper.getQgGoodsTempStockListByMap(param);
    }

    public Integer getQgGoodsTempStockCountByMap(Map<String,Object> param)throws Exception{
        return qgGoodsTempStockMapper.getQgGoodsTempStockCountByMap(param);
    }

    public Integer qdtxAddQgGoodsTempStock(QgGoodsTempStock qgGoodsTempStock)throws Exception{
            //qgGoodsTempStock.setCreatedTime(new Date());
            return qgGoodsTempStockMapper.insertQgGoodsTempStock(qgGoodsTempStock);
    }

    public Integer qdtxModifyQgGoodsTempStock(QgGoodsTempStock qgGoodsTempStock)throws Exception{
        //qgGoodsTempStock.setUpdatedTime(new Date());
        return qgGoodsTempStockMapper.updateQgGoodsTempStock(qgGoodsTempStock);
    }

    public Integer qdtxDeleteQgGoodsTempStockById(String id)throws Exception{
        return qgGoodsTempStockMapper.deleteQgGoodsTempStockById(id);
    }

    public Integer qdtxBatchDeleteQgGoodsTempStock(String ids)throws Exception{
        Map<String,List<String>> param=new HashMap<String,List<String>>();
        String[] paramArrays=ids.split(",");
        List<String> idList=new ArrayList<String>();
            for (String temp:paramArrays){
                idList.add(temp);
            }
        param.put("ids",idList);
        return qgGoodsTempStockMapper.batchDeleteQgGoodsTempStock(param);
    }


}
