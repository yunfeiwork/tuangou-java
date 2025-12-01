package com.yunfeiwork.tuangou.partner.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 券码信息（查券和验券）
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
public class CouponInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 团购券码
     */
    private String receiptCode;
    /**
     * 已验证的团购券码
     */
    private List<String> verifiedReceiptCodes;
    /**
     * 抖音原始团购券码
     */
    private String originCode;
    /**
     * 1=验券前准备（校验券码是否可用，以及返回相关的券码信息）走的接口是输码验券校验接口
     * 2=确认验券（不传值默认是 2），走的是输入券码验券接口
     */
    private String type;
    /**
     * type=1 时表示你查券，此时接口返回的 verifyCount 表示你有几张券可以验证
     * type=2 时表示你确定要验券，表示你已经验了几张券
     */
    private int verifyCount;
    /**
     * 用户手机号，形如：185****1212 （仅美团展示）
     */
    private String mobile;
    /**
     * 商品名称 示例：超值健身月卡
     */
    private String dealTitle;
    /**
     * C端顾客实际支付金额（单位：分）
     */
    private Integer payAmount;
    /**
     * 订单ID 示例：4962000011402066331
     */
    private String orderId;
    /**
     * 套餐ID
     */
    private Long dealId;
    /**
     * 团购ID
     */
    private Long dealGroupId;
    /**
     * 抖音商品ID
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String skuId;
    /**
     * 商家系统（第三方）商品id (餐饮使用)
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String thirdSkuId;
    /**
     * 验券的结果信息
     */
    private List<ReceiptConsumeResult> result;


    /**
     * 券码核销完成的结果信息
     */
    @Data
    @EqualsAndHashCode(callSuper = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ReceiptConsumeResult {
        /**
         * 订单ID
         */
        private String orderId;
        /**
         * 团购券码
         */
        private String receiptCode;
        /**
         * 套餐ID（若验证的券所对应的商品为团购时，该字段必返回），次卡（平台次卡，不是团购次卡）和拼团不返回此参数
         */
        private Long dealId;
        /**
         * 抖音团购商品skuId
         */
        private String skuId;
        /**
         * 商家系统（第三方）商品id (餐饮使用)
         */
        private String thirdSkuId;
        /**
         * 团购ID，团购ID与套餐ID是一对多的关系（若验证的券所对应的商品为团购时，该字段必返回），次卡（平台次卡，不是团购次卡）和拼团不返回此参数
         */
        private Long dealGroupId;
        /**
         * 商品名称
         */
        private String dealTitle;
        /**
         * 商品售价
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double dealPrice;

        /**
         * 商品市场价
         */
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Double dealMarketPrice;

        /**
         * 该券码所在的订单的支付明细，如果一笔订单包含两个券码a、b，在核销a、b券码时返回信息一致，都是该订单的支付明细 (如果订单有多张券可以通过订单券码分摊金额查询接口查询分摊信息)
         */
        private List<TuangouPaymentDetailSub> paymentDetail;

        @Data
        @EqualsAndHashCode(callSuper = false)
        public static class TuangouPaymentDetailSub {
            /**
             * 支付金额
             */
            private String amount;
            /**
             * 类型说明：
             * 2：抵用券
             * 5：积分
             * 6：立减
             * 8：商户抵用券
             * 10：C端美团支付
             * 12：优惠代码
             * 15：美团立减
             * 17：商家立减
             * 18：美团商家立减
             * 21：次卡
             * 22：打折卡
             * 23：B端美团支付
             * 24：全渠道会员券
             * 25：pos支付
             * 26：线下认款平台
             * 28：商家折上折
             * 29：美团分销支付
             */
            private int amountType;
            private String paymentDetailId;
        }
    }
}
