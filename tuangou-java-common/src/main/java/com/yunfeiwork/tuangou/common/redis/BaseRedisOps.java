package com.yunfeiwork.tuangou.common.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public abstract class BaseRedisOps implements RedisOps {
    @Override
    public String getValue(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(String key, String value, long expire, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getExpire(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void expire(String key, long expire, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Lock getLock(String key) {
        throw new UnsupportedOperationException();
    }
}
