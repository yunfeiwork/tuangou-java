package com.yunfeiwork.tuangou.common.util.http;

import java.io.IOException;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface RequestExecutor<T, E> {

    /**
     * 执行http请求.
     *
     * @param uri  uri
     * @param data 数据
     * @return 响应结果
     * @throws IOException io异常
     */
    T execute(String uri, E data) throws IOException;

    /**
     * 执行http请求.
     *
     * @param uri     uri
     * @param data    数据
     * @param handler http响应处理器
     * @throws IOException io异常
     */
    void execute(String uri, E data, ResponseHandler<T> handler) throws IOException;


}
