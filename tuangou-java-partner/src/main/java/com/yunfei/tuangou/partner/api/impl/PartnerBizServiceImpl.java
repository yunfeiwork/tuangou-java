package com.yunfei.tuangou.partner.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yunfei.tuangou.common.bean.Response;
import com.yunfei.tuangou.partner.api.PartnerBizService;
import com.yunfei.tuangou.partner.api.PartnerService;
import com.yunfei.tuangou.partner.bean.*;
import com.yunfei.tuangou.partner.constant.SysConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.yunfei.tuangou.partner.constant.ApiUrlConstants.*;

/**
 * 团购验券业务接口
 *
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@RequiredArgsConstructor
public class PartnerBizServiceImpl implements PartnerBizService {

    private final PartnerService partnerService;

    @Override
    public Response<ApiBalanceResult> apiBalance() {
        return apiBalance(null);
    }

    @Override
    public Response<ApiBalanceResult> apiBalance(String appId) {
        if (StringUtils.isNotBlank(appId)) {
            partnerService.switchoverTo(appId);
        }
        String result = partnerService.post(getUrl(QUERY_API_BALANCE_URL), "{}");
        return JSON.parseObject(result, new TypeReference<Response<ApiBalanceResult>>() {
        });
    }

    @Override
    public Response<List<OpPoiInfoResult>> poiInfoList(String opPoiId, int page) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("opPoiId", opPoiId);
        jsonObject.put("page", page);
        jsonObject.put("limit", 10);
        String result = partnerService.post(getUrl(POI_INFO_LIST_URL), jsonObject.toJSONString());
        return JSON.parseObject(result, new TypeReference<Response<List<OpPoiInfoResult>>>() {
        });
    }

    @Override
    public Response<AppBindShopResult> bindMeituanShop(AppBindMeituanShop param) {
        if (StringUtils.isBlank(param.getMtAccountId())) {
            return Response.fail("美团管理员账号不能为空");
        }
        if (StringUtils.isBlank(param.getMtShopId())) {
            return Response.fail("美团门店ID不能为空");
        }
        if (StringUtils.isBlank(param.getName())) {
            return Response.fail("门店名称不能为空");
        }
        if (StringUtils.isBlank(param.getBusinessType())) {
            return Response.fail("业务类型不能为空");
        }
        String result = partnerService.post(getUrl(APP_BIND_SHOP_URL), param.toJson());
        return JSON.parseObject(result, new TypeReference<Response<AppBindShopResult>>() {
        });
    }

    @Override
    public Response<AppBindShopResult> bindDouyinShop(String dyAccountId, String dyShopId) {
        return bindDouyinShop(null, dyAccountId, dyShopId);
    }

    @Override
    public Response<AppBindShopResult> bindDouyinShop(String opPoiId, String dyAccountId, String dyShopId) {
        return bindDouyinShop(new AppBindDouyinShop(opPoiId, dyAccountId, dyShopId, SysConstants.BusinessType.DDZH));
    }

    @Override
    public Response<AppBindShopResult> bindDouyinShop(AppBindDouyinShop param) {
        if (StringUtils.isBlank(param.getDyAccountId())) {
            return Response.fail("抖音管理员账号不能为空");
        }
        if (StringUtils.isBlank(param.getDyShopId())) {
            return Response.fail("抖音门店ID不能为空");
        }
        String result = partnerService.post(getUrl(APP_BIND_SHOP_URL), param.toJson());
        return JSON.parseObject(result, new TypeReference<Response<AppBindShopResult>>() {
        });
    }

    @Override
    public Response<List<TuangouProductInfo>> queryMeituanTuangouProduct(int page, String opPoiId) {
        return queryMeituanTuangouProduct(page, opPoiId, null);
    }

    @Override
    public Response<List<TuangouProductInfo>> queryMeituanTuangouProduct(int page, String opPoiId, String dealType) {
        return queryTuangouProduct(page, opPoiId, SysConstants.Platform.MEITUAN, null, dealType);
    }

    @Override
    public Response<List<TuangouProductInfo>> queryDouyinTuangouProduct(String opPoiId, String cursor) {
        return queryDouyinTuangouProduct(opPoiId, cursor, null);
    }

    @Override
    public Response<List<TuangouProductInfo>> queryDouyinTuangouProduct(String opPoiId, String cursor, String dealType) {
        return queryTuangouProduct(-1, opPoiId, SysConstants.Platform.DOUYIN, cursor, dealType);
    }

    @Override
    public Response<List<TuangouProductInfo>> queryTuangouProduct(int page, String opPoiId, String platform, String cursor, String dealType) {
        if (StringUtils.isBlank(opPoiId)) {
            return Response.fail("opPoiId不能为空");
        }
        JSONObject param = new JSONObject();
        if (!SysConstants.Platform.DOUYIN.equals(platform)) {
            param.put("page", page);
        }
        param.put("opPoiId", opPoiId);
        param.put("platform", platform);
        if (SysConstants.Platform.DOUYIN.equals(platform)) {
            param.put("cursor", cursor);
        }
        param.put("dealType", dealType);
        String result = partnerService.post(getUrl(QUERY_TUANGOU_PRODUCT_URL), param.toJSONString());
        return JSON.parseObject(result, new TypeReference<Response<List<TuangouProductInfo>>>() {
        });
    }

    @Override
    public Response<CouponInfoResult> queryMeituanCoupon(String opPoiId, String receiptCode) {
        return queryCoupon(opPoiId, receiptCode, SysConstants.Platform.MEITUAN, SysConstants.ConsumerType.QUERY);
    }

    @Override
    public Response<CouponInfoResult> queryDouyinCoupon(String opPoiId, String receiptCode) {
        return queryCoupon(opPoiId, receiptCode, SysConstants.Platform.DOUYIN, SysConstants.ConsumerType.QUERY);
    }

    @Override
    public Response<CouponInfoResult> queryCoupon(String opPoiId, String receiptCode, String platform, String type) {
        return consumerCoupon(opPoiId, receiptCode, platform, type, null);
    }

    @Override
    public Response<CouponInfoResult> verifyMeituanCoupon(String opPoiId, String receiptCode) {
        return verifyMeituanCoupon(opPoiId, receiptCode, null);
    }

    @Override
    public Response<CouponInfoResult> verifyMeituanCoupon(String opPoiId, String receiptCode, Integer verifyCount) {
        return consumerCoupon(opPoiId, receiptCode, SysConstants.Platform.MEITUAN, SysConstants.ConsumerType.VERIFY, verifyCount);
    }

    @Override
    public Response<CouponInfoResult> verifyDouyinCoupon(String opPoiId, String receiptCode) {
        return verifyDouyinCoupon(opPoiId, receiptCode, null);
    }

    @Override
    public Response<CouponInfoResult> verifyDouyinCoupon(String opPoiId, String receiptCode, Integer verifyCount) {
        return consumerCoupon(opPoiId, receiptCode, SysConstants.Platform.DOUYIN, SysConstants.ConsumerType.VERIFY, verifyCount);
    }

    @Override
    public Response<CouponInfoResult> easyQueryCoupon(String opPoiId, String receiptCode) {
        if (StringUtils.isBlank(receiptCode)) {
            return Response.fail("券码不能为空");
        }
        int length = receiptCode.length();
        if (length <= 13) {
            return queryMeituanCoupon(opPoiId, receiptCode);
        } else if (length == 15) {
            return queryDouyinCoupon(opPoiId, receiptCode);
        } else {
            return Response.fail("未找到券码所属平台");
        }
    }

    @Override
    public Response<CouponInfoResult> easyVerifyCoupon(String opPoiId, String receiptCode, Integer verifyCount) {
        if (StringUtils.isBlank(receiptCode)) {
            return Response.fail("券码不能为空");
        }
        int length = receiptCode.length();
        if (length <= 13) {
            return verifyMeituanCoupon(opPoiId, receiptCode, verifyCount);
        } else if (length == 15) {
            return verifyDouyinCoupon(opPoiId, receiptCode, verifyCount);
        } else {
            return Response.fail("未找到券码所属平台");
        }
    }

    @Override
    public Response<CouponInfoResult> consumerCoupon(String opPoiId, String receiptCode, String platform, String type, Integer verifyCount) {
        if (StringUtils.isBlank(opPoiId)) {
            return Response.fail("opPoiId不能为空");
        }
        if (StringUtils.isBlank(receiptCode)) {
            return Response.fail("券码不能为空");
        }
        JSONObject param = new JSONObject();
        param.put("opPoiId", opPoiId);
        param.put("receiptCode", receiptCode);
        param.put("platform", platform);
        param.put("type", type);
        param.put("verifyCount", verifyCount);
        String result = partnerService.post(getUrl(CONSUMER_COUPON_URL), param.toJSONString());
        return JSON.parseObject(result, new TypeReference<Response<CouponInfoResult>>() {
        });
    }

    private String getUrl(String path) {
        return StringUtils.isNotEmpty(partnerService.getPartnerConfig().getApiHostUrl()) ? String.format(path, partnerService.getPartnerConfig().getApiHostUrl()) : String.format(path, API_HOST);
    }
}
