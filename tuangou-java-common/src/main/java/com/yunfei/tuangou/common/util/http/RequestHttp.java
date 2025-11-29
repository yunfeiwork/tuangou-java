package com.yunfei.tuangou.common.util.http;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface RequestHttp<H, P> {
    /**
     * 返回httpClient.
     *
     * @return 返回httpClient
     */
    H getRequestHttpClient();

    /**
     * 返回httpProxy.
     *
     * @return 返回httpProxy
     */
    P getRequestHttpProxy();

    /**
     * 返回HttpType.
     *
     * @return HttpType
     */
    HttpType getRequestType();


}
