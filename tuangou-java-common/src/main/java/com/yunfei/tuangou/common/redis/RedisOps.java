package com.yunfei.tuangou.common.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface RedisOps {

    String getValue(String key);

    void setValue(String key, String value, long expire, TimeUnit timeUnit);

    Long getExpire(String key);

    void expire(String key, long expire, TimeUnit timeUnit);

    Lock getLock(String key);
}
