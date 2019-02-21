package com.qg.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class QgGoods implements Serializable {
    //主键
    private String id;
    //商品图片
    private String goodsImg;
    //商品名称
    private String goodsName;
    //商品单价
    private Double price;
    //原始库存
    private Integer stock;
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
    public void setGoodsImg (String  goodsImg){
        this.goodsImg=goodsImg;
    }
    public  String getGoodsImg(){
        return this.goodsImg;
    }
    public void setGoodsName (String  goodsName){
        this.goodsName=goodsName;
    }
    public  String getGoodsName(){
        return this.goodsName;
    }
    public void setPrice (Double  price){
        this.price=price;
    }
    public  Double getPrice(){
        return this.price;
    }
    public void setStock (Integer  stock){
        this.stock=stock;
    }
    public  Integer getStock(){
        return this.stock;
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
