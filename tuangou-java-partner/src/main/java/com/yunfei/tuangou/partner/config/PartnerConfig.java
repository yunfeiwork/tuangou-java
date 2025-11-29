package com.yunfei.tuangou.partner.config;

import com.yunfei.tuangou.common.bean.PartnerAccessTokenEntity;
import com.yunfei.tuangou.common.util.http.apache.ApacheHttpClientBuilder;

import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;

/**
 * 配置类
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface PartnerConfig {

    /**
     * Gets http proxy host.
     *
     * @return the http proxy host
     */
    String getHttpProxyHost();

    /**
     * Gets http proxy port.
     *
     * @return the http proxy port
     */
    int getHttpProxyPort();

    /**
     * Gets http proxy username.
     *
     * @return the http proxy username
     */
    String getHttpProxyUsername();

    /**
     * Gets http proxy password.
     *
     * @return the http proxy password
     */
    String getHttpProxyPassword();

    /**
     * http 请求重试间隔
     */
    int getRetrySleepMillis();

    /**
     * http 请求最大重试次数
     */
    int getMaxRetryTimes();

    /**
     * http client builder
     *
     * @return ApacheHttpClientBuilder apache http client builder
     */
    ApacheHttpClientBuilder getApacheHttpClientBuilder();

    String getAppId();

    String getAppSecret();

    /**
     * 设置自定义的apiHost地址
     *
     * @param apiHostUrl api域名地址
     */
    void setApiHostUrl(String apiHostUrl);

    /**
     * 获取自定义的apiHost地址，用于替换原请求中的http://wspacebot.yunfeiwork.com
     *
     * @return 自定义的api域名地址
     */
    String getApiHostUrl();

    /**
     * 获取自定义的获取accessToken地址，用于向自定义统一服务获取accessToken
     *
     * @return 自定义的获取accessToken地址
     */
    String getAccessTokenUrl();

    void setAccessTokenUrl(String accessTokenUrl);

    /**
     * 是否自动刷新token
     *
     * @return the boolean
     */
    boolean autoRefreshToken();

    /**
     * Gets access token.
     *
     * @return the access token
     */
    String getAccessToken();

    /**
     * Gets access token lock.
     *
     * @return the access token lock
     */
    Lock getAccessTokenLock();

    /**
     * Is access token expired boolean.
     *
     * @return the boolean
     */
    boolean isAccessTokenExpired();

    /**
     * 强制将access token过期掉
     */
    void expireAccessToken();

    void updateAccessToken(String accessToken, long expiresInSeconds);

    default void setUpdateAccessTokenBefore(Consumer<PartnerAccessTokenEntity> updateAccessTokenBefore) {
    }

    default void updateAccessTokenBefore(PartnerAccessTokenEntity accessTokenEntity) {
    }

    /**
     * Gets expires time.
     *
     * @return the expires time
     */
    long getExpiresTime();

    default void updateAccessTokenProcessor(String accessToken, long expiresInSeconds) {
        PartnerAccessTokenEntity accessTokenEntity = new PartnerAccessTokenEntity();
        accessTokenEntity.setAppId(getAppId());
        accessTokenEntity.setAccessToken(accessToken);
        accessTokenEntity.setExpiresIn(expiresInSeconds);
        updateAccessTokenBefore(accessTokenEntity);
        updateAccessToken(accessToken, expiresInSeconds);
    }
}
