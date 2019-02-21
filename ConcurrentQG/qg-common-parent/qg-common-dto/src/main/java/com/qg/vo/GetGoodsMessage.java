package com.qg.vo;

import java.io.Serializable;
import java.util.Date;

/***
 * 抢购消息类
 * 需要将用户id和抢购id的字符串写入消息中间件，所以定义这个类
 *
 * 因为可能会在各大组件之间进行传输，需要实现序列化接口，否则会报错
 */
public class GetGoodsMessage implements Serializable{

    private String userId;

    private String goodsId;

    private Date createdDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
