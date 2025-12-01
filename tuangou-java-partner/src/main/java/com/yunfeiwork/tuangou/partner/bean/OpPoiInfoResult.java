package com.yunfeiwork.tuangou.partner.bean;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 门店信息
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
public class OpPoiInfoResult implements Serializable {

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
    /**
     * 过期时间（年月日时分秒）字符串格式
     */
    private String expiresTimes;

    /**
     * 授权平台
     */
    private List<AuthorizePlatform> authorizePlatforms;

    @Data
    public static class AuthorizePlatform implements Serializable {
        /**
         * 平台名称 meituan=美团，douyin=抖音
         */
        private String platform;
        /**
         * 授权状态 0=未授权 1=已授权 2=授权失败 3=授权已过期
         */
        private Integer status;
        /**
         * 过期时间（秒）
         */
        private Long expiresIn;
        /**
         * 过期时间（年月日时分秒）
         */
        private LocalDateTime expiresTime;

        /**
         * 门店ID
         */
        private String shopId;
        /**
         * 点评门店id
         */
        private String dpShopId;
        /**
         * 账号ID
         */
        private String accountId;
    }
}
