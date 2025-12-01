package com.yunfeiwork.tuangou.common.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PartnerAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * access_token
     */
    private String accessToken;
    /**
     * 过期时间（秒）
     */
    private long expiresIn;
    /**
     * 过期时间（年月日时分秒）
     */
    private LocalDateTime expiresTime;
}
