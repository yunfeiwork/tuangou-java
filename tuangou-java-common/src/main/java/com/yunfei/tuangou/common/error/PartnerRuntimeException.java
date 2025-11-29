package com.yunfei.tuangou.common.error;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public class PartnerRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 4881698481192264412L;

    public PartnerRuntimeException(Throwable e) {
        super(e);
    }

    public PartnerRuntimeException(String msg) {
        super(msg);
    }

    public PartnerRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}
