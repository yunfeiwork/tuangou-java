## 本地生活团购 Java SDK [在线文档](https://wspace.yunfeiwork.com/apihub/index.html?channelCode=javasdk)

### [聚合团购核销](https://wspace.yunfeiwork.com/apihub/index.html?channelCode=javasdk)`Java`开发工具包，支持包括美团、大众点评、抖音的团购券码核销、团购商品信息查询。

### 重要信息
1. 商务合作洽谈请联系微信`18201986514`（在微信里自行搜索并添加好友，请注明来意） 或点击[【联系作者】](https://wspace.yunfeiwork.com/apihub/index.html?channelCode=javasdk)
2. 新手重要提示：本项目仅是一个SDK开发工具包，未提供Web实现，建议使用 `maven` 或 `gradle` 引用本项目即可使用本SDK提供的各种功能
3. 新手或者Java开发新手提问或新开Issue提问前，请先阅读[【常见问题汇总】](https://gitee.com/yunfeiwork/aggregation-verification-api/wikis/QA)，并确保已查阅过 [【抖音美团团购核销API接口文档】](https://gitee.com/yunfeiwork/aggregation-verification-api/wikis/Home) ，避免浪费大家的宝贵时间；


### 其他说明
1. **阅读源码的同学请注意，本SDK为简化代码编译时加入了`lombok`支持，如果不了解`lombok`的话，请先学习下相关知识**
2. 如有新功能需求，发现BUG，或者由于官方接口调整导致的代码问题，可以直接在[【Issues】](https://gitee.com/yunfeiwork/yunfei-tuangou-java/issues)页提出issue，便于讨论追踪问题；
3. 目前本`SDK`最新版本要求的`JDK`最低版本是`8`，而其他更早的JDK版本则需要自己改造实现。

### 模块说明
- tuangou-java-common: SDK公共模块, 如：工具类、异常处理等
- tuangou-java-partner: 聚合团购各接口实现模块, 如：美团、大众点评、抖音查券、查团购商品信息、核销验券等

### Maven引用方式
#### pom.xml
```xml
<repositories>
    <repository>
        <id>github</id>
        <name>GitHub Packages</name>
        <url>https://maven.pkg.github.com/yunfeiwork/tuangou-java</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.yunfeiwork</groupId>
        <artifactId>tuangou-java-partner</artifactId>
        <version>1.0.B</version>
    </dependency>
</dependencies>

```
![输入图片说明](/static/img.png "屏幕截图")

#### settings.xml
找到`settings.xml`文件，在`<servers>`中添加如下代码
```xml
<!-- 如遇到配置后报错或提示401 Unauthorized 请主动联系我方技术人员 -->
<server>
    <id>github</id>
    <username>yunfeiwork</username>
    <password>获取最新password请查看https://gitee.com/yunfeiwork/aggregation-verification-api/wikis/Home</password>
</server>
```
![输入图片说明](/static/img_1.png "屏幕截图")

### 调用案例
```java

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
    // Response<List<OpPoiInfoResult>> poiInfoResponse = partnerBizService.poiInfoList(null, 1);
    // System.out.println(JSON.toJSONString(poiInfoResponse));

    // 查询美团团购产品
    // Response<List<TuangouProductInfo>> listResponse = partnerBizService.queryMeituanTuangouProduct(1, "绑定门店成功后返回的opPoiId");
    // System.out.println(JSON.toJSONString(listResponse));

    // 查询抖音团购产品
    // Response<List<TuangouProductInfo>> tuangouProductResponse = partnerBizService.queryDouyinTuangouProduct("绑定门店成功后返回的opPoiId", null);
    // System.out.println(JSON.toJSONString(tuangouProductResponse));

    // 查询抖音团购券信息
    // Response<CouponInfoResult> couponInfoResultResponse = partnerBizService.queryDouyinCoupon("绑定门店成功后返回的opPoiId", "1075910001132098");
    // System.out.println(JSON.toJSONString(couponInfoResultResponse));
}
```

### 技术支持
![输入图片说明](https://foruda.gitee.com/images/1742530950212709239/695f98b7_15605968.png "屏幕截图")
- 只负责技术支持，咨询接口调用费用，请加商务的企业微信
- **问问题请携带提供给您的 `appId` 接口返回的 `tid` 等请求参数以及问题截图**
- **工作时间：9:30~18:30 ( 周一至周五)**

### 商务合作请扫码添加下方二维码
![输入图片说明](https://foruda.gitee.com/images/1742530810715595208/707a4c19_15605968.png "屏幕截图")
