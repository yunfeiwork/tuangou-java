package com.yunfei.tuangou.partner.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiBalanceResult implements Serializable {
    /**
     * 余额，人民币1:1 抵扣
     */
    private BigDecimal balance;
}
