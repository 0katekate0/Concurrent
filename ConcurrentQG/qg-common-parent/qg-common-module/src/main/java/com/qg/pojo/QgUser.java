package com.qg.pojo;
import java.io.Serializable;
import java.util.Date;
/***
*   用户表
*/
public class QgUser implements Serializable {
    //主键
    private String id;
    //手机号
    private String phone;
    //密码
    private String password;
    //
    private String wxUserId;
    //真实姓名
    private String realName;
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
    public void setPhone (String  phone){
        this.phone=phone;
    }
    public  String getPhone(){
        return this.phone;
    }
    public void setPassword (String  password){
        this.password=password;
    }
    public  String getPassword(){
        return this.password;
    }
    public void setWxUserId (String  wxUserId){
        this.wxUserId=wxUserId;
    }
    public  String getWxUserId(){
        return this.wxUserId;
    }
    public void setRealName (String  realName){
        this.realName=realName;
    }
    public  String getRealName(){
        return this.realName;
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
