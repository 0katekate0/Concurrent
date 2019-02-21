package com.qg.mapper;
import com.qg.pojo.QgGoods;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QgGoodsMapper {

	public QgGoods getQgGoodsById(@Param(value = "id") String id)throws Exception;

	public List<QgGoods>	getQgGoodsListByMap(Map<String,Object> param)throws Exception;

	public Integer getQgGoodsCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertQgGoods(QgGoods qgGoods)throws Exception;

	public Integer updateQgGoods(QgGoods qgGoods)throws Exception;

	public Integer deleteQgGoodsById(@Param(value = "id") String id)throws Exception;

	public Integer batchDeleteQgGoods(Map<String,List<String>> params);

}
