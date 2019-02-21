package com.qg.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class QgGoodsTempStock implements Serializable {
    //主键
    private String id;
    //商品图片
    private String goodsId;
    //
    private String userId;
    //状态(0:锁定库存,1:成功支付,2:支付超时)
    private Integer status;
    //
    private Date createdTime;
    //
    private Date updatedTime;
    //get set 方法
    public void setId (String  id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }
    public void setGoodsId (String  goodsId){
        this.goodsId=goodsId;
    }
    public  String getGoodsId(){
        return this.goodsId;
    }
    public void setUserId (String  userId){
        this.userId=userId;
    }
    public  String getUserId(){
        return this.userId;
    }
    public void setStatus (Integer  status){
        this.status=status;
    }
    public  Integer getStatus(){
        return this.status;
    }
    public void setCreatedTime (Date  createdTime){
        this.createdTime=createdTime;
    }
    public  Date getCreatedTime(){
        return this.createdTime;
    }
    public void setUpdatedTime (Date  updatedTime){
        this.updatedTime=updatedTime;
    }
    public  Date getUpdatedTime(){
        return this.updatedTime;
    }
}
