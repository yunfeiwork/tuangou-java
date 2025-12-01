package com.yunfeiwork.tuangou.partner.config.impl;

import com.yunfeiwork.tuangou.common.util.http.apache.ApacheHttpClientBuilder;
import com.yunfeiwork.tuangou.partner.config.PartnerConfig;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认的配置实现，基于内存，在实际生产环境中应该将这些配置持久化
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public class PartnerDefaultConfigImpl implements PartnerConfig {

    protected volatile String appId;
    private volatile String appSecret;
    protected volatile String accessToken;
    private volatile long expiresTime;

    protected Lock accessTokenLock = new ReentrantLock();

    private volatile String httpProxyHost;
    private volatile int httpProxyPort;
    private volatile String httpProxyUsername;
    private volatile String httpProxyPassword;

    private volatile int retrySleepMillis = 1000;
    private volatile int maxRetryTimes = 5;

    private volatile ApacheHttpClientBuilder apacheHttpClientBuilder;
    private String apiHostUrl;
    private String accessTokenUrl;

    @Override
    public String getHttpProxyHost() {
        return this.httpProxyHost;
    }

    public void setHttpProxyHost(String httpProxyHost) {
        this.httpProxyHost = httpProxyHost;
    }

    @Override
    public int getHttpProxyPort() {
        return this.httpProxyPort;
    }

    public void setHttpProxyPort(int httpProxyPort) {
        this.httpProxyPort = httpProxyPort;
    }

    @Override
    public String getHttpProxyUsername() {
        return this.httpProxyUsername;
    }

    public void setHttpProxyUsername(String httpProxyUsername) {
        this.httpProxyUsername = httpProxyUsername;
    }

    @Override
    public String getHttpProxyPassword() {
        return this.httpProxyPassword;
    }

    public void setHttpProxyPassword(String httpProxyPassword) {
        this.httpProxyPassword = httpProxyPassword;
    }

    @Override
    public int getRetrySleepMillis() {
        return this.retrySleepMillis;
    }

    public void setRetrySleepMillis(int retrySleepMillis) {
        this.retrySleepMillis = retrySleepMillis;
    }

    @Override
    public int getMaxRetryTimes() {
        return this.maxRetryTimes;
    }

    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public ApacheHttpClientBuilder getApacheHttpClientBuilder() {
        return this.apacheHttpClientBuilder;
    }

    public void setApacheHttpClientBuilder(ApacheHttpClientBuilder apacheHttpClientBuilder) {
        this.apacheHttpClientBuilder = apacheHttpClientBuilder;
    }

    @Override
    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public void setApiHostUrl(String apiHostUrl) {
        this.apiHostUrl = apiHostUrl;
    }

    @Override
    public String getApiHostUrl() {
        return this.apiHostUrl;
    }

    @Override
    public String getAccessTokenUrl() {
        return this.accessTokenUrl;
    }

    @Override
    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    @Override
    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Lock getAccessTokenLock() {
        return this.accessTokenLock;
    }

    public void setAccessTokenLock(Lock accessTokenLock) {
        this.accessTokenLock = accessTokenLock;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return isExpired(this.expiresTime);
    }

    /**
     * 判断 expiresTime 是否已经过期
     */
    protected boolean isExpired(long expiresTime) {
        return System.currentTimeMillis() > expiresTime;
    }

    @Override
    public void expireAccessToken() {
        this.expiresTime = 0;
    }

    @Override
    public long getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = expiresTime;
    }

    @Override
    public void updateAccessToken(String accessToken, long expiresInSeconds) {
        setAccessToken(accessToken);
        setExpiresTime(expiresAheadInMillis(expiresInSeconds));
    }

    /**
     * 会过期的数据提前过期时间，默认预留200秒的时间
     */
    protected long expiresAheadInMillis(long expiresInSeconds) {
        return System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }
}
