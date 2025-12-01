package com.yunfeiwork.tuangou.common.bean;


import com.yunfeiwork.tuangou.common.ens.CodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author <a href="https://gitee.com/yunfeiwork">yunfeiwork</a>
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Response<T> implements Serializable {

    private int code;

    private String msg;

    private int count = 0;

    private T data;

    /**
     * 源自抖音团购需要 查询商品线上数据时 接口返回的next_cursor传入进行翻页
     */
    private String nextCursor;

    /**
     * 日志id,用于快速定位问题
     */
    private String tid;

    public Response() {}

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Response<T> fail(String msg) {
        return new Response<T>(CodeEnum.FAIL.getCode(), msg);
    }

}
