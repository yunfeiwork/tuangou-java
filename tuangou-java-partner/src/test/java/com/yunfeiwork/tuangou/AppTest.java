package com.yunfeiwork.tuangou;


import com.alibaba.fastjson.JSON;
import com.yunfeiwork.tuangou.common.bean.Response;
import com.yunfeiwork.tuangou.partner.api.PartnerBizService;
import com.yunfeiwork.tuangou.partner.api.impl.PartnerServiceImpl;
import com.yunfeiwork.tuangou.partner.bean.*;
import com.yunfeiwork.tuangou.partner.config.impl.PartnerDefaultConfigImpl;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {

    // 这里只是展示，请自行实现sprign注入StringRedisTemplate
    // @Autowired
    // private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        PartnerServiceImpl partnerService = new PartnerServiceImpl();
        // 纯内存管理token
        PartnerDefaultConfigImpl config = new PartnerDefaultConfigImpl();
        config.setAppId("你的appId");
        config.setAppSecret("你的appSecret");

        // （推荐）如果你希望使用redis管理token，请使用如下代码，请注入StringRedisTemplate
        // RedisTemplateRedisOps redisOps = new RedisTemplateRedisOps(stringRedisTemplate);
        // PartnerRedisConfigImpl config = new PartnerRedisConfigImpl(redisOps, "tuangou_sdk");

        //String accessToken = partnerService.getAccessToken();
        //System.out.println(accessToken);

        PartnerBizService partnerBizService = partnerService.getPartnerBizService();

        // 查询余额
        Response<ApiBalanceResult> apiBalanceResponse = partnerBizService.apiBalance();
        System.out.println(JSON.toJSONString(apiBalanceResponse));

        // 绑定到店综合门店
        //Response<AppBindShopResult> appBindShopResultResponse = partnerBizService.bindMeituanShop(AppBindMeituanShop.ddzhShop("美团账号Id", "美团账号名称", "美团门店Id", "抖音门店Id", "门店名称", "门店地址", "城市名称"));
        //System.out.println(JSON.toJSONString(appBindShopResultResponse));

        // 绑定到店餐饮门店
        //Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.queryMeituanCoupon("绑定门店成功后返回的opPoiId", "0103607253231");
        //System.out.println(JSON.toJSONString(couponInfoResultResponse));

        // 绑定抖音门店
        Response<AppBindShopResult> appBindShopResultResponse = partnerBizService.bindDouyinShop( "抖音账号ID", "抖音门店ID");
        //Response<AppBindShopResult> appBindShopResultResponse = partnerBizService.bindDouyinShop("绑定美团门店成功后返回的opPoiId", "抖音账号ID", "抖音门店ID");

        // 查门店信息
        //Response<List<OpPoiInfoResult>> poiInfoResponse = partnerBizService.poiInfoList(null, 1);
        //System.out.println(JSON.toJSONString(poiInfoResponse));

        // 查询美团团购产品
        Response<List<TuangouProductInfo>> listResponse = partnerBizService.queryMeituanTuangouProduct(1, "绑定门店成功后返回的opPoiId");
        System.out.println(JSON.toJSONString(listResponse));

        // 查询抖音团购产品
        Response<List<TuangouProductInfo>> tuangouProductResponse = partnerBizService.queryDouyinTuangouProduct("绑定门店成功后返回的opPoiId", null);
        System.out.println(JSON.toJSONString(tuangouProductResponse));

        // 查询团购券信息（自动区分是美团券还是抖音券）
        Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.easyQueryCoupon("绑定门店成功后返回的opPoiId", "0103607253231");
        System.out.println(JSON.toJSONString(couponInfoResultResponse));

        // 查询美团团购券信息
        //Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.queryMeituanCoupon("绑定门店成功后返回的opPoiId", "0103607253231");

        // 查询抖音团购券信息
        //Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.queryDouyinCoupon("绑定门店成功后返回的opPoiId", "1075910001130098");
        //System.out.println(JSON.toJSONString(couponInfoResultResponse));
    }
}
