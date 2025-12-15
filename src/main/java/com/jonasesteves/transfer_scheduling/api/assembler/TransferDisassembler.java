package com.jonasesteves.transfer_scheduling.api.assembler;

import com.jonasesteves.transfer_scheduling.api.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.domain.entity.Transfer;
import com.jonasesteves.transfer_scheduling.domain.utility.IdGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferDisassembler {

    public Transfer toDomainModel(TransferInput transferInput) {
        return Transfer.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .beneficiary(transferInput.getBeneficiary())
                .amount(transferInput.getAmount())
                .feeAmount(BigDecimal.ZERO)
                .scheduledAt(transferInput.getScheduledAt())
                .build();
    }
}
