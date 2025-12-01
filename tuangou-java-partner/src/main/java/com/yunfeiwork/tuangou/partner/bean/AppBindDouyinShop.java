package com.yunfeiwork.tuangou.partner.bean;

import com.alibaba.fastjson.JSON;
import com.yunfeiwork.tuangou.partner.constant.SysConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 抖音门店绑定信息
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AppBindDouyinShop implements Serializable {
    /**
     * 门店ID，如果和之前已经绑定的美团门店是同一商家可以共用一个opPoiId，如有就传；反之，不传会生成新的opPoiId
     */
    private String opPoiId;
    /**
     * 抖音来客账户ID
     * 获取抖音来客账户ID请查看文档: https://doc.weixin.qq.com/doc/w3_ANkA-QbyANACNZ6ls6iH8SX23D0qQ?scode=ALUAawcbABAsHuX0GuANkA-QbyANA
     */
    private String dyAccountId;
    /**
     * 抖音来客门店ID
     * 获取门店ID请查看文档: https://doc.weixin.qq.com/doc/w3_ANkA-QbyANACNZ6ls6iH8SX23D0qQ?scode=ALUAawcbABAsHuX0GuANkA-QbyANA
     */
    private String dyShopId;
    /**
     * 业务类型：DDZH=到店综合，DDCY=到店餐饮
     */
    private String businessType;
    /**
     * 平台：douyin=抖音
     */
    private String platform;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public AppBindDouyinShop(String dyAccountId, String dyShopId, String businessType) {
        this.dyAccountId = dyAccountId;
        this.dyShopId = dyShopId;
        this.businessType = businessType;
        this.platform = SysConstants.Platform.DOUYIN;
    }

    public AppBindDouyinShop(String opPoiId, String dyAccountId, String dyShopId, String businessType) {
        this.opPoiId = opPoiId;
        this.dyAccountId = dyAccountId;
        this.dyShopId = dyShopId;
        this.businessType = businessType;
        this.platform = SysConstants.Platform.DOUYIN;
    }
}
