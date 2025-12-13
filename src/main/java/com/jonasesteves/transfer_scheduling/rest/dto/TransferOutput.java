package com.jonasesteves.transfer_scheduling.rest.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransferOutput {

    private UUID id;
    private String beneficiary;
    private BigDecimal amount;
    private BigDecimal feeAmount;
    private OffsetDateTime scheduledAt;

    public TransferOutput(UUID id, String beneficiary, BigDecimal amount, BigDecimal feeAmount, OffsetDateTime scheduledAt) {
        this.id = id;
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.feeAmount = feeAmount;
        this.scheduledAt = scheduledAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
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
        private UUID id;
        private String beneficiary;
        private BigDecimal amount;
        private BigDecimal feeAmount;
        private OffsetDateTime scheduledAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder beneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder feeAmount(BigDecimal feeAmount) {
            this.feeAmount = feeAmount;
            return this;
        }

        public Builder scheduledAt(OffsetDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public TransferOutput build() {
            return new TransferOutput(id, beneficiary, amount, feeAmount, scheduledAt);
        }
    }
}
