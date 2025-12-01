package com.yunfeiwork.tuangou.partner.api;

import com.yunfeiwork.tuangou.common.bean.Response;
import com.yunfeiwork.tuangou.partner.bean.*;

import java.util.List;

/**
 * <pre>
 * 聚合验券服务相关接口
 *
 * 文档地址：https://gitee.com/yunfeiwork/aggregation-verification-api/wikis/Home
 * 常见问题：https://gitee.com/yunfeiwork/aggregation-verification-api/wikis/QA
 * </pre>
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface PartnerBizService {

    /**
     * 查询余额
     *
     * @return balance
     */
    Response<ApiBalanceResult> apiBalance();

    /**
     * 获取余额
     *
     * @param appId 指定appId
     * @return balance
     */
    Response<ApiBalanceResult> apiBalance(String appId);

    /**
     * 已授权门店查询
     *
     * @param opPoiId 门店ID，若为空则查当前应用绑定的所有门店
     * @param page    页码
     * @return 门店信息
     */
    Response<List<OpPoiInfoResult>> poiInfoList(String opPoiId, int page);

    /**
     * 绑定美团门店
     *
     * @param param 绑定门店参数
     * @return 绑定门店结果
     */
    Response<AppBindShopResult> bindMeituanShop(AppBindMeituanShop param);

    /**
     * 绑定抖音门店
     *
     * @param dyAccountId 抖音账号ID
     * @param dyShopId    抖音门店ID
     * @return 绑定门店结果
     */
    Response<AppBindShopResult> bindDouyinShop(String dyAccountId, String dyShopId);

    /**
     * 绑定抖音门店
     *
     * @param opPoiId     门店ID，如果和之前已经绑定的美团门店是同一商家可以共用一个opPoiId，如有就传；反之，不传会生成新的opPoiId
     * @param dyAccountId 抖音账号ID
     * @param dyShopId    抖音门店ID
     * @return 绑定门店结果
     */
    Response<AppBindShopResult> bindDouyinShop(String opPoiId, String dyAccountId, String dyShopId);

    /**
     * 绑定抖音门店
     *
     * @param param 绑定门店参数
     * @return 绑定门店结果
     */
    Response<AppBindShopResult> bindDouyinShop(AppBindDouyinShop param);

    /**
     * 查询美团团购产品信息
     *
     * @param page    页码
     * @param opPoiId 门店ID
     * @return 美团团购产品信息
     */
    Response<List<TuangouProductInfo>> queryMeituanTuangouProduct(int page, String opPoiId);

    /**
     * 查询美团团购产品信息
     *
     * @param page     页码
     * @param opPoiId  门店ID
     * @param dealType 团购类型 1-团购套餐，2-代金券 ( 暂时用于到店餐饮 ) 非必传字段
     * @return 美团团购产品信息
     */
    Response<List<TuangouProductInfo>> queryMeituanTuangouProduct(int page, String opPoiId, String dealType);

    /**
     * 获取抖音团购产品信息
     *
     * @param opPoiId 抖音门店ID
     * @param cursor  游标（抖音翻页必传字段）
     * @return 抖音团购产品信息
     */
    Response<List<TuangouProductInfo>> queryDouyinTuangouProduct(String opPoiId, String cursor);

    /**
     * 获取抖音团购产品信息
     *
     * @param opPoiId 抖音门店ID
     * @param cursor  游标（抖音翻页必传字段）
     * @param dealType 团购类型 1-团购套餐，2-代金券 ( 暂时用于到店餐饮 ) 非必传字段
     * @return 抖音团购产品信息
     */
    Response<List<TuangouProductInfo>> queryDouyinTuangouProduct(String opPoiId, String cursor, String dealType);

    Response<List<TuangouProductInfo>> queryTuangouProduct(int page, String opPoiId, String platform, String cursor, String dealType);

    Response<CouponInfoResult> queryMeituanCoupon(String opPoiId, String receiptCode);

    Response<CouponInfoResult> queryDouyinCoupon(String opPoiId, String receiptCode);

    Response<CouponInfoResult> queryCoupon(String opPoiId, String receiptCode, String platform, String type);

    Response<CouponInfoResult> verifyMeituanCoupon(String opPoiId, String receiptCode);

    Response<CouponInfoResult> verifyMeituanCoupon(String opPoiId, String receiptCode, Integer verifyCount);

    Response<CouponInfoResult> verifyDouyinCoupon(String opPoiId, String receiptCode);

    Response<CouponInfoResult> verifyDouyinCoupon(String opPoiId, String receiptCode, Integer verifyCount);

    /**
     * 查询团购券信息（自动区分是美团券还是抖音券）
     *
     * @param opPoiId     门店ID
     * @param receiptCode 券码
     * @return 团购券信息
     */
    Response<CouponInfoResult> easyQueryCoupon(String opPoiId, String receiptCode);

    /**
     * 验证核销团购券（自动区分是美团券还是抖音券）
     *
     * @param opPoiId     门店ID
     * @param receiptCode 券码
     * @param verifyCount 验券前准备返回的核销数
     * @return 验券结果
     */
    Response<CouponInfoResult> easyVerifyCoupon(String opPoiId, String receiptCode, Integer verifyCount);

    /**
     * 查券、验券接口
     *
     * @param opPoiId     门店ID
     * @param receiptCode 券码
     * @param platform    团购平台：meituan=美团（大众点评），douyin=抖音（如果没传值则默认美团）
     * @param type        验券类型：1=验券前准备（查券）， 2=确认验券（核销券）（如果没传值则默认确认验券）
     * @param verifyCount type=1 不传 type=2（确认验券时必传，不传则默认取验券前准备返回的总数）<a href="https://gitee.com/yunfeiwork/aggregation-verification-api/wikis/QA#q%E6%9F%A5%E5%88%B8%E9%AA%8C%E5%88%B8%E6%8E%A5%E5%8F%A3-verifycount-%E5%AD%97%E6%AE%B5%E5%A6%82%E4%BD%95%E4%BC%A0%E5%80%BC">verifyCount 字段如何传值</a>
     * @return 领取结果
     */
    Response<CouponInfoResult> consumerCoupon(String opPoiId, String receiptCode, String platform, String type, Integer verifyCount);
}
