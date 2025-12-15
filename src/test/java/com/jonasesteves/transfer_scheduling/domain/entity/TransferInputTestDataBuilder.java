package com.jonasesteves.transfer_scheduling.domain.entity;

import com.jonasesteves.transfer_scheduling.api.dto.TransferInput;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransferInputTestDataBuilder {

    public static TransferInput.Builder someTransferInput() {
        return TransferInput.builder()
                .beneficiary("PT501234")
                .amount(new BigDecimal("1000.00"))
                .scheduledAt(OffsetDateTime.now().plusDays(1));
    }
}
