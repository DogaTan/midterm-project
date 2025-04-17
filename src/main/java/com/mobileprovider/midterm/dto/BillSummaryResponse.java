package com.mobileprovider.midterm.dto;

import java.math.BigDecimal;

public class BillSummaryResponse {
    private BigDecimal totalAmount;
    private boolean isPaid;

    public BillSummaryResponse(BigDecimal totalAmount, boolean isPaid) {
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }
}
