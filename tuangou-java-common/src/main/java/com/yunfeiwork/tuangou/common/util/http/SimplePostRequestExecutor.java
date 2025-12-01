package com.yunfeiwork.tuangou.common.util.http;

import com.yunfeiwork.tuangou.common.ens.CodeEnum;
import com.yunfeiwork.tuangou.common.error.BaseException;
import com.yunfeiwork.tuangou.common.util.http.apache.ApacheSimplePostRequestExecutor;

import java.io.IOException;
import java.util.Objects;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public abstract class SimplePostRequestExecutor<H, P> implements RequestExecutor<String, String> {

    protected RequestHttp<H, P> requestHttp;

    public SimplePostRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler) throws IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        if (Objects.requireNonNull(requestHttp.getRequestType()) == HttpType.APACHE_HTTP) {
            return new ApacheSimplePostRequestExecutor(requestHttp);
        }
        throw new IllegalArgumentException("非法请求参数");
    }

    public String handleResponse(String responseContent) throws BaseException {
        if (responseContent.isEmpty()) {
            throw new BaseException(CodeEnum.FAIL.getCode(), "无响应内容");
        }
        return responseContent;
    }
}
