package com.yunfei.tuangou.partner.bean;

import com.alibaba.fastjson.JSON;
import com.yunfei.tuangou.partner.constant.SysConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 美团门店绑定信息
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppBindMeituanShop implements Serializable {

    private String opPoiId;
    /**
     * 美团门店id
     */
    private String mtShopId;
    /**
     * 点评门店ID
     */
    private String dpShopId;
    /**
     * 管理员账号
     */
    private String mtAccountId;
    /**
     * 商家资质 -> 公司名称 or 账号中文名称
     */
    private String mtAccountName;
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
     * 业务类型：DDZH=到店综合，DDCY=到店餐饮
     */
    private String businessType;

    private String platform;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public AppBindMeituanShop(String mtAccountId, String mtAccountName, String mtShopId, String dpShopId, String name, String address, String cityName, String businessType) {
        this.mtAccountId = mtAccountId;
        this.mtAccountName = mtAccountName;
        this.mtShopId = mtShopId;
        this.dpShopId = dpShopId;
        this.name = name;
        this.address = address;
        this.cityName = cityName;
        this.businessType = businessType;
        this.platform = SysConstants.Platform.MEITUAN;
    }

    public static AppBindMeituanShop ddzhShop(String mtAccountId, String mtAccountName, String mtShopId, String dpShopId, String name, String address, String cityName) {
        return new AppBindMeituanShop(mtAccountId, mtAccountName, mtShopId, dpShopId, name, address, cityName, SysConstants.BusinessType.DDZH);
    }

    public static AppBindMeituanShop ddcyShop(String mtAccountId, String mtAccountName, String mtShopId, String dpShopId, String name, String address, String cityName) {
        return new AppBindMeituanShop(mtAccountId, mtAccountName, mtShopId, dpShopId, name, address, cityName, SysConstants.BusinessType.DDZH);
    }
}
