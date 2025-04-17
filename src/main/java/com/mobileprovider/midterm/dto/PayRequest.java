package com.mobileprovider.midterm.dto;

import java.math.BigDecimal;

public class PayRequest {
    private String subscriberNo;
    private Integer month;
    private Integer year;
    private BigDecimal amount;

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
