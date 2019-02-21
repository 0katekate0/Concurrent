package com.qg.mapper;
import com.qg.pojo.QgTrade;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QgTradeMapper {

	public QgTrade getQgTradeById(@Param(value = "id") String id)throws Exception;

	public List<QgTrade>	getQgTradeListByMap(Map<String,Object> param)throws Exception;

	public Integer getQgTradeCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertQgTrade(QgTrade qgTrade)throws Exception;

	public Integer updateQgTrade(QgTrade qgTrade)throws Exception;

	public Integer deleteQgTradeById(@Param(value = "id") String id)throws Exception;

	public Integer batchDeleteQgTrade(Map<String,List<String>> params);

}
