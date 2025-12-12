package com.jonasesteves.transfer_scheduling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transfer {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String beneficiary;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal feeAmount;

    @CreatedBy
    private UUID createdByUserId;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    public Transfer() {
    }

    public Transfer(UUID id, String beneficiary, BigDecimal amount, BigDecimal feeAmount) {
        this.id = id;
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.feeAmount = feeAmount;
    }

    public Transfer(UUID id, String beneficiary, BigDecimal amount, BigDecimal feeAmount, UUID createdByUserId, OffsetDateTime createdAt, UUID lastModifiedByUserId, OffsetDateTime lastModifiedAt) {
        this.id = id;
        this.beneficiary = beneficiary;
        this.amount = amount;
        this.feeAmount = feeAmount;
        this.createdByUserId = createdByUserId;
        this.createdAt = createdAt;
        this.lastModifiedByUserId = lastModifiedByUserId;
        this.lastModifiedAt = lastModifiedAt;
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
        Objects.requireNonNull(beneficiary);
        this.beneficiary = beneficiary;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        Objects.requireNonNull(amount);
        this.amount = amount;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        Objects.requireNonNull(amount);
        this.feeAmount = feeAmount;
    }

    public UUID getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(UUID createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getLastModifiedByUserId() {
        return lastModifiedByUserId;
    }

    public void setLastModifiedByUserId(UUID lastModifiedByUserId) {
        this.lastModifiedByUserId = lastModifiedByUserId;
    }

    public OffsetDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(OffsetDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(id, transfer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "id=" + id +
                ", beneficiary='" + beneficiary + '\'' +
                ", amount=" + amount +
                ", feeAmount=" + feeAmount +
                ", createdByUserId=" + createdByUserId +
                ", createdAt=" + createdAt +
                ", lastModifiedByUserId=" + lastModifiedByUserId +
                ", lastModifiedAt=" + lastModifiedAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private String beneficiary;
        private BigDecimal amount;
        private BigDecimal feeAmount;

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

        public Transfer build() {
            return new Transfer(id, beneficiary, amount, feeAmount);
        }

        @Override
        public String toString() {
            return "Transfer.Builder{" +
                    "id=" + id +
                    ", beneficiary='" + beneficiary + '\'' +
                    ", amount=" + amount +
                    ", feeAmount=" + feeAmount +
                    '}';
        }
    }
}
