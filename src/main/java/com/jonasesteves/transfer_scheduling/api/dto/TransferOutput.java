package com.jonasesteves.transfer_scheduling.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransferOutput {

    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", description = "Unique identifier of the transfer")
    private UUID id;

    @Schema(example = "PT501234", description = "Beneficiary account identifier")
    private String beneficiary;

    @Schema(example = "452.50", description = "Amount to be transferred")
    private BigDecimal amount;

    @Schema(example = "40.72", description = "Fee amount for the transfer")
    private BigDecimal feeAmount;

    @Schema(example = "2024-12-31T15:30:00Z", description = "Scheduled date and time for the transfer")
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
