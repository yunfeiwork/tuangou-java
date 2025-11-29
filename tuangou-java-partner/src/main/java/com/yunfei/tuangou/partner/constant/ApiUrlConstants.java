package com.yunfei.tuangou.partner.constant;

public interface ApiUrlConstants {

    String API_HOST = "http://wspacebot.yunfeiwork.com";
    /**
     * 获取access_token
     */
    String GET_ACCESS_TOKEN_URL = "%s/wspace-openapi/oauth/token";

    /**
     * 查询余额
     */
    String QUERY_API_BALANCE_URL = "%s/wspace-openapi/partner/apibalance";

    /**
     * 已授权门店查询
     */
    String POI_INFO_LIST_URL = "%s/wspace-openapi/partner/tuangou/pageQueryPoiList";

    /**
     * 应用绑定门店
     */
    String APP_BIND_SHOP_URL = "%s/wspace-openapi/partner/app/bind/shop";

    /**
     * 获取门店团购信息
     */
    String QUERY_TUANGOU_PRODUCT_URL = "%s/wspace-openapi/partner/tuangou/queryshopdeal";
    /**
     * 消费券
     */
    String CONSUMER_COUPON_URL = "%s/wspace-openapi/partner/tuangou/receipt/consume";
}
