package com.jonasesteves.transfer_scheduling.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransferInput {

    @Schema(example = "PT501234", description = "Beneficiary account identifier")
    @NotBlank
    private String beneficiary;

    @Schema(example = "1500.75", description = "Amount to be transferred", minimum = "1.00")
    @NotNull
    private BigDecimal amount;

    @Schema(example = "2024-12-31T15:30:00Z", description = "Scheduled date and time for the transfer. Cannot be in the past.")
    @NotNull
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
