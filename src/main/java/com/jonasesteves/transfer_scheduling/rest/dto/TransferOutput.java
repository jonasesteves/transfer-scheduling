package com.jonasesteves.transfer_scheduling.rest.dto;

import java.math.BigDecimal;

public class TransferOutput {

    private BigDecimal amount;
    private BigDecimal feeAmount;

    public TransferOutput(BigDecimal amount, BigDecimal feeAmount) {
        this.amount = amount;
        this.feeAmount = feeAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }
}
