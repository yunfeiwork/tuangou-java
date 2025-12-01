package com.yunfeiwork.tuangou.common.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PartnerAccessTokenEntity extends PartnerAccessToken {
    private String appId;
}
