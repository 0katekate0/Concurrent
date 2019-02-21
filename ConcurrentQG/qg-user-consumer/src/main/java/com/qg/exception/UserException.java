package com.qg.exception;
/***
 * 用户业务异常
 */
public enum  UserException {
    USER_PASSWORD_ERROR(1001,"用户名或密码错误");
    private Integer code;
    private String message;

    UserException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

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
