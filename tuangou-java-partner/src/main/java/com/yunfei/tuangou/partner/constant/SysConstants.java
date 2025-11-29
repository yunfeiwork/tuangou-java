package com.yunfei.tuangou.partner.constant;

public interface SysConstants {
    interface Platform {
        String MEITUAN = "meituan";
        String DOUYIN = "douyin";
    }

    interface BusinessType {
        String DDZH = "DDZH";
        String DDCY = "DDCY";
    }

    interface ConsumerType {
        /**
         * 查询
         */
        String QUERY = "1";
        /**
         * 核销
         */
        String VERIFY = "2";
    }
}
