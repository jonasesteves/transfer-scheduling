package com.jonasesteves.transfer_scheduling.service;

import com.jonasesteves.transfer_scheduling.entity.Transfer;
import com.jonasesteves.transfer_scheduling.repository.TransferRepository;
import com.jonasesteves.transfer_scheduling.rest.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.rest.dto.TransferOutput;
import com.jonasesteves.transfer_scheduling.utility.IdGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    private final TransferRepository repository;

    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public TransferOutput save(TransferInput transferInput) {
        Transfer transfer = Transfer.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .beneficiary(transferInput.getBeneficiary())
                .amount(transferInput.getAmount())
                .feeAmount(BigDecimal.ZERO)
                .build();

        Transfer savedTransfer = repository.save(transfer);
        return new TransferOutput(savedTransfer.getAmount(), BigDecimal.ZERO);
    }
}
