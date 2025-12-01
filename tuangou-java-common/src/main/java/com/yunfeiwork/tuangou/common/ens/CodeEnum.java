package com.yunfeiwork.tuangou.common.ens;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public enum CodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    FAIL(555, "失败"),
    ERROR(500, "内部异常"),
    TOKEN_NOT_EXIST(11003, "token不存在"),
    TOKEN_NOT_AUTHORIZED(11004, "token无授权，token已失效或被撤销授权"),
    ;

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

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
