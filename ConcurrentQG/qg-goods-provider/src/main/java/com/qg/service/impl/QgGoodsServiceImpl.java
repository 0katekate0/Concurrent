package com.qg.service.impl;
import com.qg.mapper.QgGoodsMapper;
import com.qg.pojo.QgGoods;
import com.qg.service.QgGoodsService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Service(interfaceClass=QgGoodsService.class)
public class QgGoodsServiceImpl implements QgGoodsService {

    @Resource
    private QgGoodsMapper qgGoodsMapper;

    public QgGoods getQgGoodsById(String id)throws Exception{
        return qgGoodsMapper.getQgGoodsById(id);
    }

    public List<QgGoods>	getQgGoodsListByMap(Map<String,Object> param)throws Exception{
        return qgGoodsMapper.getQgGoodsListByMap(param);
    }

    public Integer getQgGoodsCountByMap(Map<String,Object> param)throws Exception{
        return qgGoodsMapper.getQgGoodsCountByMap(param);
    }

    public Integer qdtxAddQgGoods(QgGoods qgGoods)throws Exception{
            //qgGoods.setCreatedTime(new Date());
            return qgGoodsMapper.insertQgGoods(qgGoods);
    }

    public Integer qdtxModifyQgGoods(QgGoods qgGoods)throws Exception{
        //qgGoods.setUpdatedTime(new Date());
        return qgGoodsMapper.updateQgGoods(qgGoods);
    }

    public Integer qdtxDeleteQgGoodsById(String id)throws Exception{
        return qgGoodsMapper.deleteQgGoodsById(id);
    }

    public Integer qdtxBatchDeleteQgGoods(String ids)throws Exception{
        Map<String,List<String>> param=new HashMap<String,List<String>>();
        String[] paramArrays=ids.split(",");
        List<String> idList=new ArrayList<String>();
            for (String temp:paramArrays){
                idList.add(temp);
            }
        param.put("ids",idList);
        return qgGoodsMapper.batchDeleteQgGoods(param);
    }


}
