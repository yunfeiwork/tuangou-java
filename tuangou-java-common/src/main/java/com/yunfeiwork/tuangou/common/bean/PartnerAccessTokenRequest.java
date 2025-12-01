package com.yunfeiwork.tuangou.common.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
public class PartnerAccessTokenRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String appId;
    private String appSecret;
    private boolean forceRefresh;

    public String toJson() {
        return JSON.toJSONString(this);
    }

}
