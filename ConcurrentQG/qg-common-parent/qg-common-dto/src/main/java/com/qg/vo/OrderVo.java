package com.qg.vo;

import java.io.Serializable;

/***
 * 返回订单信息的VO
 *
 * 这个对象需要传输，需要实现序列化接口
 */
public class OrderVo implements Serializable{
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

    //商品图片
    private String goodsImg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
