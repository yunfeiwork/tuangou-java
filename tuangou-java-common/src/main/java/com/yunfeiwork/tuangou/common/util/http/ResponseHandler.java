package com.yunfeiwork.tuangou.common.util.http;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface ResponseHandler<T> {
    /**
     * 响应结果处理.
     *
     * @param t 要处理的对象
     */
    void handle(T t);
}
