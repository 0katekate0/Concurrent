package com.qg.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class QgOrder implements Serializable {
    //主键
    private String id;
    //
    private String userId;
    //库存Id
    private String stockId;
    //订单编号
    private String orderNo;
    //商品Id
    private String goodsId;
    //购买量
    private Integer num;
    //总价
    private Double amount;
    //状态(0：待支付 1：支付成功 2:支付失败)
    private Integer status;
    //阿里支付交易号
    private String aliTradeNo;
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
    public void setUserId (String  userId){
        this.userId=userId;
    }
    public  String getUserId(){
        return this.userId;
    }
    public void setStockId (String  stockId){
        this.stockId=stockId;
    }
    public  String getStockId(){
        return this.stockId;
    }
    public void setOrderNo (String  orderNo){
        this.orderNo=orderNo;
    }
    public  String getOrderNo(){
        return this.orderNo;
    }
    public void setGoodsId (String  goodsId){
        this.goodsId=goodsId;
    }
    public  String getGoodsId(){
        return this.goodsId;
    }
    public void setNum (Integer  num){
        this.num=num;
    }
    public  Integer getNum(){
        return this.num;
    }
    public void setAmount (Double  amount){
        this.amount=amount;
    }
    public  Double getAmount(){
        return this.amount;
    }
    public void setStatus (Integer  status){
        this.status=status;
    }
    public  Integer getStatus(){
        return this.status;
    }
    public void setAliTradeNo (String  aliTradeNo){
        this.aliTradeNo=aliTradeNo;
    }
    public  String getAliTradeNo(){
        return this.aliTradeNo;
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
