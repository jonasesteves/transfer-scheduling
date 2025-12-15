package com.jonasesteves.transfer_scheduling.api.assembler;

import com.jonasesteves.transfer_scheduling.domain.entity.Transfer;
import com.jonasesteves.transfer_scheduling.api.dto.TransferOutput;
import org.springframework.stereotype.Component;

@Component
public class TransferAssembler {

    public TransferOutput toTransferOutput(Transfer transfer) {
        return TransferOutput.builder()
                .id(transfer.getId())
                .beneficiary(transfer.getBeneficiary())
                .amount(transfer.getAmount())
                .feeAmount(transfer.getFeeAmount())
                .scheduledAt(transfer.getScheduledAt())
                .build();
    }
}
