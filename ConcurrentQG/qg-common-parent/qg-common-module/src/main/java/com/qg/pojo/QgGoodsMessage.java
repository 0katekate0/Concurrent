package com.qg.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class QgGoodsMessage implements Serializable {
    //主键
    private String id;
    //用户ID
    private String userId;
    //消息ID
    private String goodsId;
    //抢购状态
    private String status;
    //金额
    private Double amount;
    //创建时间
    private Date createdTime;
    //更新时间
    private Date updatedTime;
    //get set 方法
    public void setId (String  id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }
    public void setUserId (String  userId){
        this.userId=userId;
    }
    public  String getUserId(){
        return this.userId;
    }
    public void setGoodsId (String  goodsId){
        this.goodsId=goodsId;
    }
    public  String getGoodsId(){
        return this.goodsId;
    }
    public void setStatus (String  status){
        this.status=status;
    }
    public  String getStatus(){
        return this.status;
    }
    public void setAmount (Double  amount){
        this.amount=amount;
    }
    public  Double getAmount(){
        return this.amount;
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
