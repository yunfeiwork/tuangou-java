package com.yunfeiwork.tuangou.partner.config.impl;

import com.yunfeiwork.tuangou.common.redis.RedisOps;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis存储的配置类
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public class PartnerRedisConfigImpl extends PartnerDefaultConfigImpl {

    private static final String ACCESS_TOKEN_KEY_TPL = "%s:access_token:%s";
    private static final String LOCK_KEY_TPL = "%s:lock:%s:";

    private final RedisOps redisOps;
    private final String keyPrefix;

    private volatile String accessTokenKey;
    private volatile String lockKey;

    public PartnerRedisConfigImpl(RedisOps redisOps, String keyPrefix) {
        this.redisOps = redisOps;
        this.keyPrefix = keyPrefix;
    }

    @Override
    public void setAppId(String appId) {
        super.setAppId(appId);
        this.accessTokenKey = String.format(ACCESS_TOKEN_KEY_TPL, this.keyPrefix, appId);
        this.lockKey = String.format(LOCK_KEY_TPL, this.keyPrefix, appId);
        super.accessTokenLock = this.redisOps.getLock(lockKey.concat("accessTokenLock"));
    }

    @Override
    public String getAccessToken() {
        return redisOps.getValue(this.accessTokenKey);
    }

    @Override
    public boolean isAccessTokenExpired() {
        Long expire = redisOps.getExpire(this.accessTokenKey);
        return expire == null || expire < 2;
    }

    @Override
    public synchronized void updateAccessToken(String accessToken, long expiresInSeconds) {
        redisOps.setValue(this.accessTokenKey, accessToken, expiresInSeconds - 200, TimeUnit.SECONDS);
    }

    @Override
    public void expireAccessToken() {
        redisOps.expire(this.accessTokenKey, 0, TimeUnit.SECONDS);
    }

    @Override
    public String toString() {
        return "PartnerRedisConfigImpl{" +
                "appId='" + appId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenLock=" + accessTokenLock +
                ", redisOps=" + redisOps +
                ", keyPrefix='" + keyPrefix + '\'' +
                ", accessTokenKey='" + accessTokenKey + '\'' +
                ", lockKey='" + lockKey + '\'' +
                '}';
    }
}
