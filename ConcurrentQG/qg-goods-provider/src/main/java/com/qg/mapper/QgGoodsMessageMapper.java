package com.qg.mapper;
import com.qg.pojo.QgGoodsMessage;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QgGoodsMessageMapper {

	public QgGoodsMessage getQgGoodsMessageById(@Param(value = "id") String id)throws Exception;

	public List<QgGoodsMessage>	getQgGoodsMessageListByMap(Map<String,Object> param)throws Exception;

	public Integer getQgGoodsMessageCountByMap(Map<String,Object> param)throws Exception;

	public Integer insertQgGoodsMessage(QgGoodsMessage qgGoodsMessage)throws Exception;

	public Integer updateQgGoodsMessage(QgGoodsMessage qgGoodsMessage)throws Exception;

	public Integer deleteQgGoodsMessageById(@Param(value = "id") String id)throws Exception;

	public Integer batchDeleteQgGoodsMessage(Map<String,List<String>> params);

}
