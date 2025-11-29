package com.yunfei.tuangou.partner.api;

import com.yunfei.tuangou.common.service.HttpService;
import com.yunfei.tuangou.common.util.http.RequestExecutor;
import com.yunfei.tuangou.common.util.http.RequestHttp;
import com.yunfei.tuangou.partner.config.PartnerConfig;

import java.util.Map;
import java.util.function.Function;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
public interface PartnerService extends HttpService {
    /**
     * 初始化http请求对象.
     */
    void initHttp();

    /**
     * 请求http请求相关信息.
     *
     * @return request http
     */
    RequestHttp getRequestHttp();

    /**
     *  获取access_token, 不强制刷新access_token.
     *
     * @return access_token
     */
    String getAccessToken();

    String getAccessToken(boolean forceRefresh);

    PartnerBizService getPartnerBizService();

    /**
     * 注入 {@link PartnerConfig} 的实现.
     *
     * @param partnerConfig config
     */
    void setPartnerConfig(PartnerConfig partnerConfig);

    PartnerConfig getPartnerConfig();

    /**
     * 从 Map中 移除 {@link String miniappId} 所对应的 {@link PartnerConfig}，适用于动态移除应用配置.
     *
     * @param appId 应用ID
     */
    void removeConfig(String appId);

    /**
     * 注入多个 {@link PartnerConfig} 的实现. 并为每个 {@link PartnerConfig} 赋予不同的 {@link String appId} 值 随机采用一个{@link
     * String appId}进行Http初始化操作
     *
     * @param configs PartnerConfig map
     */
    void setMultiConfigs(Map<String, PartnerConfig> configs);

    /**
     * 注入多个 {@link PartnerConfig} 的实现. 并为每个 {@link PartnerConfig} 赋予不同的 {@link String label} 值
     *
     * @param configs      PartnerConfig map
     * @param defaultAppId 设置一个{@link PartnerConfig} 所对应的{@link String defaultAppId}进行Http初始化
     */
    void setMultiConfigs(Map<String, PartnerConfig> configs, String defaultAppId);

    /**
     * 进行相应的小程序切换.
     *
     * @param appId 应用ID
     * @return 切换成功 ，则返回当前对象，方便链式调用，否则抛出异常
     */
    PartnerService switchoverTo(String appId);

    /**
     * 进行相应的应用切换.
     *
     * @param appId 应用ID
     * @param func  当对应的小程序配置不存在时，允许通过函数的方式进行调用获取
     * @return 切换成功 ，则返回当前对象，方便链式调用，否则抛出异常
     */
    PartnerService switchoverTo(String appId, Function<String, PartnerConfig> func);

    /**
     * Map里 加入新的 {@link PartnerConfig}，适用于动态添加新的应用配置.
     *
     * @param appId         应用ID
     * @param configStorage 新的应用配置
     */
    void addConfig(String appId, PartnerConfig configStorage);

    <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data);

}
