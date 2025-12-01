package com.yunfeiwork.tuangou.common.error;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public class BaseException extends RuntimeException {

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
