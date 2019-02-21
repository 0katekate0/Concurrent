package com.qg.exception;

/***
 * 商品项目异常
 */
public enum  OrderException {
    ORDER_NOT_EXIST(1201, "订单不存在");

    OrderException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
