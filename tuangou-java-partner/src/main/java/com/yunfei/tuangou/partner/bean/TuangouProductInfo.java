package com.yunfei.tuangou.partner.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 团购商品信息
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TuangouProductInfo implements Serializable {
    /**
     * 套餐ID（美团返回）
     */
    private Long dealId;
    /**
     * 抖音团购商品skuId  示例：1807530707458068
     */
    private String skuId;
    /**
     * 商品ID
     */
    private String productId;
    /**
     * 商家商品ID
     */
    private String thirdSkuId;

    /**
     * 团购ID（美团返回）
     */
    private Long dealGroupId;

    /**
     * 团购开始售卖时间（美团返回）
     */
    private String beginDate;

    /**
     * 团购结束售卖时间（美团返回）
     */
    private String endDate;

    /**
     * （团购）套餐名称
     */
    private String title;

    /**
     * （团购）套餐-实际销售价格
     */
    private Double price;

    /**
     * （团购）套餐原价
     */
    private Double marketPrice;
    /**
     * 团购券开始服务时间（美团返回）
     */
    private String receiptBeginDate;

    /**
     * 团购券结束服务时间（美团返回）
     */
    private String receiptEndDate;

    /**
     * 售卖状态 1-未开始售卖，2-售卖中，3-售卖结束（美团返回）
     */
    private Integer saleStatus;
    /**
     * 团购状态 1-在售团单 ，2-隐藏单（美团返回）
     */
    private Integer dealGroupStatus;

    /**
     * 团购类型 1-团购套餐，2-代金券（美团返回）
     */
    private String dealType;
}
