package com.jonasesteves.transfer_scheduling.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TransferInput {

    @NotBlank
    private String beneficiary;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "amount must be at least 1.00")
    @Digits(integer = 18, fraction = 2, message = "amount must have up to 2 decimal places")
    private BigDecimal amount;

    public TransferInput(String beneficiary, BigDecimal amount) {
        this.beneficiary = beneficiary;
        this.amount = amount;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
