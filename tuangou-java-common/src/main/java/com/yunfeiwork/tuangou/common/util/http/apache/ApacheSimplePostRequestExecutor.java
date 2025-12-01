package com.yunfeiwork.tuangou.common.util.http.apache;

import com.yunfeiwork.tuangou.common.util.http.RequestHttp;
import com.yunfeiwork.tuangou.common.util.http.SimplePostRequestExecutor;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public class ApacheSimplePostRequestExecutor extends SimplePostRequestExecutor<CloseableHttpClient, HttpHost> {

    public ApacheSimplePostRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, String postEntity) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        if (postEntity != null) {
            StringEntity entity = new StringEntity(postEntity, Consts.UTF_8);
            entity.setContentType("application/json; charset=utf-8");
            httpPost.setHeader("sdk-version","1.0.B");
            httpPost.setEntity(entity);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            return this.handleResponse(responseContent);
        } finally {
            httpPost.releaseConnection();
        }
    }
}
