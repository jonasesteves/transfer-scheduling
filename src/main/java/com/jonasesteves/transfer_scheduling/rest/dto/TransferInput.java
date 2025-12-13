package com.jonasesteves.transfer_scheduling.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransferInput {

    @NotBlank
    private String beneficiary;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "amount must be at least 1.00")
    @Digits(integer = 18, fraction = 2, message = "amount must have up to 2 decimal places")
    private BigDecimal amount;

    @NotNull
    @FutureOrPresent
    private OffsetDateTime scheduledAt;

    public TransferInput(String beneficiary, BigDecimal amount, OffsetDateTime scheduledAt) {
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.scheduledAt = scheduledAt;
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

    public OffsetDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(OffsetDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String beneficiary;
        private BigDecimal amount;
        private OffsetDateTime scheduledAt;

        public Builder beneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder scheduledAt(OffsetDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public TransferInput build() {
            return new TransferInput(beneficiary, amount, scheduledAt);
        }
    }
}
