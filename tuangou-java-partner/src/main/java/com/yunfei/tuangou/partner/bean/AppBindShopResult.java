package com.yunfei.tuangou.partner.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 应用绑定门店结果
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppBindShopResult implements Serializable {
    /**
     * 门店id
     */
    private String opPoiId;
    /**
     * 门店名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 城市
     */
    private String cityName;
    /**
     * 过期时间（秒）
     */
    private Long expiresIn;
    /**
     * 过期时间（年月日时分秒）
     */
    private LocalDateTime expiresTime;
}
