package com.jonasesteves.transfer_scheduling.rest.controller;

import com.jonasesteves.transfer_scheduling.entity.Transfer;
import com.jonasesteves.transfer_scheduling.repository.TransferRepository;
import com.jonasesteves.transfer_scheduling.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

@DataJpaTest
class TransferControllerIT {

    private final TransferRepository transferRepository;

    @Autowired
    TransferControllerIT(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Test
    void shouldSaveNewTransfer() {
        Transfer transfer = Transfer.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .amount(BigDecimal.valueOf(1000))
                .build();

        Transfer savedTransfer = transferRepository.save(transfer);

        Assertions.assertThat(savedTransfer.getAmount()).isEqualTo(transfer.getAmount());
    }
}