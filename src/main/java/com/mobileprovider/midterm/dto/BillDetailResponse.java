package com.mobileprovider.midterm.dto;

import java.math.BigDecimal;

public class BillDetailResponse {
    private Integer phoneMinutes;
    private Integer internetMb;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private Boolean isPaid;

    public BillDetailResponse(Integer phoneMinutes, Integer internetMb, BigDecimal totalAmount,
                              BigDecimal paidAmount, Boolean isPaid) {
        this.phoneMinutes = phoneMinutes;
        this.internetMb = internetMb;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.isPaid = isPaid;
    }

    // Getters
    public Integer getPhoneMinutes() {
        return phoneMinutes;
    }

    public Integer getInternetMb() {
        return internetMb;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }
}
