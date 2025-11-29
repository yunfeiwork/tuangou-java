package com.yunfei.tuangou.partner.api.impl;

import com.yunfei.tuangou.common.bean.PartnerAccessTokenRequest;
import com.yunfei.tuangou.common.util.http.HttpType;
import com.yunfei.tuangou.common.util.http.apache.ApacheHttpClientBuilder;
import com.yunfei.tuangou.common.util.http.apache.DefaultApacheHttpClientBuilder;
import com.yunfei.tuangou.partner.config.PartnerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

import static com.yunfei.tuangou.partner.constant.ApiUrlConstants.API_HOST;
import static com.yunfei.tuangou.partner.constant.ApiUrlConstants.GET_ACCESS_TOKEN_URL;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Slf4j
public class PartnerServiceHttpClientImpl extends BasePartnerServiceImpl {

    private CloseableHttpClient httpClient;
    private HttpHost httpProxy;

    @Override
    public void initHttp() {
        PartnerConfig configStorage = this.getPartnerConfig();
        ApacheHttpClientBuilder apacheHttpClientBuilder = configStorage.getApacheHttpClientBuilder();
        if (null == apacheHttpClientBuilder) {
            apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        }

        apacheHttpClientBuilder.httpProxyHost(configStorage.getHttpProxyHost())
                .httpProxyPort(configStorage.getHttpProxyPort())
                .httpProxyUsername(configStorage.getHttpProxyUsername())
                .httpProxyPassword(configStorage.getHttpProxyPassword());

        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();
    }

    @Override
    protected String doGetAccessTokenRequest(boolean forceRefresh) throws IOException {
        String url = StringUtils.isNotEmpty(this.getPartnerConfig().getAccessTokenUrl()) ? this.getPartnerConfig().getAccessTokenUrl() : StringUtils.isNotEmpty(this.getPartnerConfig().getApiHostUrl()) ?
                String.format(GET_ACCESS_TOKEN_URL, this.getPartnerConfig().getApiHostUrl()) : String.format(GET_ACCESS_TOKEN_URL, API_HOST);

        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        try {
            httpPost = new HttpPost(url);
            if (this.getRequestHttpProxy() != null) {
                RequestConfig config = RequestConfig.custom().setProxy(this.getRequestHttpProxy()).build();
                httpPost.setConfig(config);
            }
            PartnerAccessTokenRequest accessTokenRequest = new PartnerAccessTokenRequest();
            accessTokenRequest.setAppId(this.getPartnerConfig().getAppId());
            accessTokenRequest.setAppSecret(this.getPartnerConfig().getAppSecret());
            accessTokenRequest.setForceRefresh(forceRefresh);
            httpPost.setEntity(new StringEntity(accessTokenRequest.toJson(), ContentType.APPLICATION_JSON));
            response = getRequestHttpClient().execute(httpPost);
            return new BasicResponseHandler().handleResponse(response);
        } finally {
            if (httpPost != null) {
                httpPost.releaseConnection();
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.APACHE_HTTP;
    }
}
