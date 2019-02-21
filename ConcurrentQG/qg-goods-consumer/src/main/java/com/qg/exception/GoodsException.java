package com.qg.exception;

/***
 * 商品项目异常
 */
public enum  GoodsException {
    USER_REPEAT_GET(1101,"您已抢购过该商品"),
    GOODS_IS_CLEAR(1102,"商品已经被抢购一空"),
    GOODS_IS_GETTING(1103,"商品正在抢购");
    GoodsException(Integer code, String message) {
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
