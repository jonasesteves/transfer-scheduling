package com.jonasesteves.transfer_scheduling.entity;

import com.jonasesteves.transfer_scheduling.utility.IdGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransferTestDataBuilder {

    public static Transfer.Builder someTransfer() {
        return Transfer.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .beneficiary("PT501234")
                .amount(new BigDecimal("1000.00"))
                .scheduledAt(OffsetDateTime.now())
                .feeAmount(BigDecimal.ZERO);
    }
}
