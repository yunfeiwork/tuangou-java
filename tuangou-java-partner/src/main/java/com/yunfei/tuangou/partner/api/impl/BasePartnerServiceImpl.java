package com.yunfei.tuangou.partner.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yunfei.tuangou.common.bean.PartnerAccessToken;
import com.yunfei.tuangou.common.bean.Response;
import com.yunfei.tuangou.common.ens.CodeEnum;
import com.yunfei.tuangou.common.error.BaseException;
import com.yunfei.tuangou.common.error.PartnerRuntimeException;
import com.yunfei.tuangou.common.util.http.RequestExecutor;
import com.yunfei.tuangou.common.util.http.RequestHttp;
import com.yunfei.tuangou.common.util.http.SimplePostRequestExecutor;
import com.yunfei.tuangou.partner.api.PartnerBizService;
import com.yunfei.tuangou.partner.api.PartnerService;
import com.yunfei.tuangou.partner.config.PartnerConfig;
import com.yunfei.tuangou.partner.util.PartnerConfigHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

import static com.yunfei.tuangou.partner.constant.ApiUrlConstants.API_HOST;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Slf4j
public abstract class BasePartnerServiceImpl<H, P> implements PartnerService, RequestHttp<H, P> {

    private Map<String, PartnerConfig> configMap = new HashMap<>();

    private int maxRetryTimes = 5;

    private int retrySleepMillis = 1000;

    private final PartnerBizService partnerBizService = new PartnerBizServiceImpl(this);


    @Override
    public RequestHttp getRequestHttp() {
        return this;
    }

    @Override
    public void setPartnerConfig(PartnerConfig partnerConfig) {
        final String appId = partnerConfig.getAppId();
        Map<String, PartnerConfig> map = new HashMap<>();
        map.put(appId, partnerConfig);
        Map<String, PartnerConfig> configMap = Collections.unmodifiableMap(map);
        this.setMultiConfigs(configMap, appId);
    }

    @Override
    public void removeConfig(String appId) {
        synchronized (this) {
            if (this.configMap.size() == 1) {
                this.configMap.remove(appId);
                log.warn("已删除最后一个应用配置：{}，须立即使用setPartnerConfig或setMultiConfigs添加配置", appId);
                return;
            }
            if (PartnerConfigHolder.get().equals(appId)) {
                this.configMap.remove(appId);
                final String defaultAppId = this.configMap.keySet().iterator().next();
                PartnerConfigHolder.set(defaultAppId);
                log.warn("已删除默认应用配置，应用【{}】被设为默认配置", defaultAppId);
                return;
            }
            this.configMap.remove(appId);
        }
    }

    @Override
    public void setMultiConfigs(Map<String, PartnerConfig> configs) {
        this.setMultiConfigs(configs, configs.keySet().iterator().next());
    }

    @Override
    public void setMultiConfigs(Map<String, PartnerConfig> configs, String defaultAppId) {
        // 防止覆盖配置
        if (this.configMap != null) {
            this.configMap.putAll(configs);
        } else {
            this.configMap = new HashMap<>(configs);
        }
        PartnerConfigHolder.set(defaultAppId);
        this.initHttp();
    }

    @Override
    public PartnerConfig getPartnerConfig() {
        if (this.configMap.size() == 1) {
            // 只有一个appId，直接返回其配置即可
            return this.configMap.values().iterator().next();
        }

        return this.configMap.get(PartnerConfigHolder.get());
    }


    @Override
    public PartnerService switchoverTo(String appId) {
        return switchoverTo(appId, null);
    }

    @Override
    public PartnerService switchoverTo(String appId, Function<String, PartnerConfig> func) {
        if (this.configMap.containsKey(appId)) {
            PartnerConfigHolder.set(appId);
            return this;
        }

        if (func != null) {
            PartnerConfig config = func.apply(appId);
            if (config != null) {
                this.addConfig(appId, config);
                return this;
            }
        }

        throw new PartnerRuntimeException(String.format("无法找到对应【%s】的配置信息，请核实！", appId));
    }

    @Override
    public void addConfig(String appId, PartnerConfig configStorages) {
        synchronized (this) {
            if (this.configMap != null && !this.configMap.isEmpty()) {
                PartnerConfigHolder.set(appId);
                this.configMap.put(appId, configStorages);
            } else {
                this.setPartnerConfig(configStorages);
            }
        }
    }

    @Override
    public String getAccessToken() {
        return getAccessToken(false);
    }

    /**
     * 设置当前的AccessToken
     *
     * @param resultContent 响应内容
     * @return access token
     */
    protected String extractAccessToken(String resultContent) {
        log.debug("access-token response: {}", resultContent);
        PartnerConfig config = this.getPartnerConfig();
        Response<PartnerAccessToken> response = JSON.parseObject(resultContent, new TypeReference<Response<PartnerAccessToken>>() {
        });
        if (response.getCode() != CodeEnum.SUCCESS.getCode()) {
            throw new BaseException(response.getCode(), response.getMsg());
        }

        PartnerAccessToken accessToken = response.getData();
        config.updateAccessTokenProcessor(accessToken.getAccessToken(), accessToken.getExpiresIn());
        return accessToken.getAccessToken();
    }

    protected abstract String doGetAccessTokenRequest(boolean forceRefresh) throws IOException;

    @Override
    public String getAccessToken(boolean forceRefresh) {
        if (!forceRefresh && !this.getPartnerConfig().isAccessTokenExpired()) {
            return this.getPartnerConfig().getAccessToken();
        }

        Lock lock = this.getPartnerConfig().getAccessTokenLock();
        boolean locked = false;
        try {
            do {
                locked = lock.tryLock(100, TimeUnit.MILLISECONDS);
                if (!forceRefresh && !this.getPartnerConfig().isAccessTokenExpired()) {
                    return this.getPartnerConfig().getAccessToken();
                }
            } while (!locked);

            String response = doGetAccessTokenRequest(forceRefresh);
            return extractAccessToken(response);
        } catch (IOException | InterruptedException e) {
            throw new PartnerRuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    @Override
    public PartnerBizService getPartnerBizService() {
        return this.partnerBizService;
    }

    @Override
    public String post(String url, String postData) {

        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }

    @Override
    public <R, T> R execute(RequestExecutor<R, T> executor, String uri, T data) {
        String dataForLog;
        if (data == null) {
            dataForLog = null;
        } else {
            dataForLog = data.toString();
        }

        return this.executeWithRetry((uriWithAccessToken) -> executor.execute(uriWithAccessToken, data), uri, dataForLog);
    }

    private <R> R executeWithRetry(ExecutorAction<R> executor, String uri, String dataForLog) {
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, uri, dataForLog, false);
            } catch (BaseException e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    log.warn("重试达到最大次数【{}】", maxRetryTimes);
                    // 最后一次重试失败后，直接抛出异常，不再等待
                    throw new BaseException(CodeEnum.FAIL.getCode(), "服务端异常，超出重试次数！");
                }

                // 555 系统繁忙, 1000ms后重试
                if (e.getCode() == CodeEnum.FAIL.getCode()) {
                    int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                    try {
                        log.warn("系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                        TimeUnit.MILLISECONDS.sleep(sleepMillis);
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    throw e;
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);

        log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
        throw new BaseException(CodeEnum.ERROR.getCode(), "服务端异常，超出重试次数");
    }

    private interface ExecutorAction<R> {
        R execute(String urlWithAccessToken) throws IOException;
    }

    private <R> R executeInternal(ExecutorAction<R> executor, String uri, String dataForLog, boolean doNotAutoRefreshToken) {

        String accessToken = getAccessToken(false);

        if (StringUtils.isNotEmpty(this.getPartnerConfig().getApiHostUrl())) {
            uri = uri.replace(API_HOST, this.getPartnerConfig().getApiHostUrl());
        }

        String uriWithAccessToken = uri + (uri.contains("?") ? "&" : "?") + "access_token=" + accessToken;
        try {
            R result = executor.execute(uriWithAccessToken);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uriWithAccessToken, dataForLog, result);
            return result;
        } catch (BaseException e) {
            if (CodeEnum.TOKEN_NOT_AUTHORIZED.getCode() == e.getCode()) {
                // 强制设置WxMaConfig的access token过期了，这样在下一次请求里就会刷新access token
                Lock lock = this.getPartnerConfig().getAccessTokenLock();
                lock.lock();
                try {
                    if (StringUtils.equals(this.getPartnerConfig().getAccessToken(), accessToken)) {
                        this.getPartnerConfig().expireAccessToken();
                    }
                } catch (Exception ex) {
                    this.getPartnerConfig().expireAccessToken();
                } finally {
                    lock.unlock();
                }
                if (this.getPartnerConfig().autoRefreshToken() && !doNotAutoRefreshToken) {
                    log.warn("即将重新获取新的access_token，错误代码：{}，错误信息：{}", e.getCode(), e.getMsg());
                    // 下一次不再自动重试
                    return this.executeInternal(executor, uri, dataForLog, true);
                }
            }

            if (e.getCode() == CodeEnum.FAIL.getCode()) {
                log.warn("\n【请求地址】: {}\n【请求参数】：{}\n【错误代码】：{}\n【错误信息】：{}", uriWithAccessToken, dataForLog, e.getCode(), e.getMsg());
                throw e;
            }
            return null;
        } catch (IOException e) {
            log.warn("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uriWithAccessToken, dataForLog, e.getMessage());
            throw new BaseException(CodeEnum.ERROR.getCode(), e.getMessage());
        }
    }
}
