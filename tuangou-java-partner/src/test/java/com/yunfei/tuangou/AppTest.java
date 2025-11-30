package com.yunfei.tuangou;


import com.alibaba.fastjson.JSON;
import com.yunfei.tuangou.common.bean.Response;
import com.yunfei.tuangou.partner.api.PartnerBizService;
import com.yunfei.tuangou.partner.api.impl.PartnerServiceImpl;
import com.yunfei.tuangou.partner.bean.*;
import com.yunfei.tuangou.partner.config.impl.PartnerDefaultConfigImpl;
import com.yunfei.tuangou.partner.config.impl.PartnerRedisConfigImpl;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) {
        PartnerServiceImpl partnerService = new PartnerServiceImpl();
        // 纯内存管理token
        PartnerDefaultConfigImpl config = new PartnerDefaultConfigImpl();
        config.setAppId("你的appId");
        config.setAppSecret("你的appSecret");

        // 如果你希望使用redis管理token，请使用如下代码，推荐实现
        //PartnerRedisConfigImpl config = new PartnerRedisConfigImpl(reids,keyPrefix);
        partnerService.addConfig(config.getAppId(), config);

        String accessToken = partnerService.getAccessToken();
        System.out.println(accessToken);

        PartnerBizService partnerBizService = partnerService.getPartnerBizService();

        // 查询余额
        Response<ApiBalanceResult> apiBalanceResponse = partnerBizService.apiBalance();
        System.out.println(JSON.toJSONString(apiBalanceResponse));

        // Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.queryMeituanCoupon("绑定门店成功后返回的opPoiId", "0103607253231");
        // System.out.println(JSON.toJSONString(couponInfoResultResponse));

        Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.easyQueryCoupon("绑定门店成功后返回的opPoiId", "0103607253231");
        System.out.println(JSON.toJSONString(couponInfoResultResponse));

        // 查门店信息
        Response<List<OpPoiInfoResult>> poiInfoResponse = partnerBizService.poiInfoList(null, 1);
        System.out.println(JSON.toJSONString(poiInfoResponse));

        // 查询美团团购产品
        Response<List<TuangouProductInfo>> listResponse = partnerBizService.queryMeituanTuangouProduct(1, "绑定门店成功后返回的opPoiId");
        System.out.println(JSON.toJSONString(listResponse));

        // 查询抖音团购产品
        Response<List<TuangouProductInfo>> tuangouProductResponse = partnerBizService.queryDouyinTuangouProduct("绑定门店成功后返回的opPoiId", null);
        System.out.println(JSON.toJSONString(tuangouProductResponse));

        // 查询抖音团购券信息
//        Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.queryDouyinCoupon("绑定门店成功后返回的opPoiId", "1075910001130098");
//        System.out.println(JSON.toJSONString(couponInfoResultResponse));
    }
}
