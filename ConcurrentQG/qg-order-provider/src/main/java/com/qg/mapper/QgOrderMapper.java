package com.qg.mapper;
import com.qg.pojo.QgOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QgOrderMapper {

	public QgOrder getQgOrderById(@Param(value = "id") String id)throws Exception;

	public List<QgOrder>	getQgOrderListByMap(Map<String,Object> param)throws Exception;

	public Integer getQgOrderCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertQgOrder(QgOrder qgOrder)throws Exception;

	public Integer updateQgOrder(QgOrder qgOrder)throws Exception;

	public Integer deleteQgOrderById(@Param(value = "id") String id)throws Exception;

	public Integer batchDeleteQgOrder(Map<String,List<String>> params);

}
