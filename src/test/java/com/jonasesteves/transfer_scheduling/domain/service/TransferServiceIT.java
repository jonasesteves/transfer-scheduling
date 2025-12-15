package com.jonasesteves.transfer_scheduling.domain.service;

import com.jonasesteves.transfer_scheduling.api.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.api.dto.TransferOutput;
import com.jonasesteves.transfer_scheduling.api.exception.TransferNotFoundException;
import com.jonasesteves.transfer_scheduling.domain.entity.TransferInputTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@SpringBootTest
@Transactional
class TransferServiceIT {

    private final TransferService service;

    @Autowired
    TransferServiceIT(TransferService service) {
        this.service = service;
    }

    @Test
    void shouldCreateAndFindTransfer() {
        TransferInput transfer = TransferInputTestDataBuilder.someTransferInput().build();

        TransferOutput savedTransfer = service.save(transfer);
        UUID transferId = savedTransfer.getId();

        TransferOutput foundTransfer = service.findById(transferId);

        Assertions.assertThat(foundTransfer).satisfies(
                t -> Assertions.assertThat(t.getId()).isEqualTo(transferId),
                t -> Assertions.assertThat(t.getAmount()).isEqualByComparingTo(savedTransfer.getAmount()),
                t -> Assertions.assertThat(t.getScheduledAt().truncatedTo(ChronoUnit.MILLIS)).isEqualTo(savedTransfer.getScheduledAt().truncatedTo(ChronoUnit.MILLIS)),
                t -> Assertions.assertThat(t.getFeeAmount()).isEqualByComparingTo(savedTransfer.getFeeAmount())
        );
    }

    @Test
    void shouldUpdateTransfer() {
        TransferInput transferInput = TransferInputTestDataBuilder.someTransferInput().build();

        TransferOutput savedTransfer = service.save(transferInput);
        UUID transferId = savedTransfer.getId();

        BigDecimal newAmount = new BigDecimal("1250.00");
        OffsetDateTime newScheduledAt = transferInput.getScheduledAt().plusDays(5);

        transferInput.setAmount(newAmount);
        transferInput.setScheduledAt(newScheduledAt);

        TransferOutput updatedTransfer = service.update(transferId, transferInput);

        Assertions.assertThat(updatedTransfer).satisfies(
                t -> Assertions.assertThat(t.getId()).isEqualTo(transferId),
                t -> Assertions.assertThat(t.getAmount()).isEqualTo(newAmount),
                t -> Assertions.assertThat(t.getFeeAmount()).isEqualTo(new BigDecimal("112.50")),
                t -> Assertions.assertThat(t.getScheduledAt().truncatedTo(ChronoUnit.MILLIS)).isEqualTo(newScheduledAt.truncatedTo(ChronoUnit.MILLIS))
        );
    }

    @Test
    void shouldDeleteTransfer() {
        TransferInput transferInput = TransferInputTestDataBuilder.someTransferInput().build();

        TransferOutput savedTransfer = service.save(transferInput);
        UUID transferId = savedTransfer.getId();

        service.delete(transferId);

        Assertions.assertThatExceptionOfType(TransferNotFoundException.class).isThrownBy(() -> service.findById(transferId));
    }

    @Test
    void shouldFindTransfersByDateInterval() {
        TransferInput transferInput1 = TransferInputTestDataBuilder.someTransferInput()
                .scheduledAt(OffsetDateTime.now().plusDays(10))
                .build();

        TransferInput transferInput2 = TransferInputTestDataBuilder.someTransferInput()
                .scheduledAt(OffsetDateTime.now().plusDays(20))
                .build();

        TransferInput transferInput3 = TransferInputTestDataBuilder.someTransferInput()
                .scheduledAt(OffsetDateTime.now().plusDays(30))
                .build();

        service.save(transferInput1);
        service.save(transferInput2);
        service.save(transferInput3);

        OffsetDateTime startDate = OffsetDateTime.now();
        OffsetDateTime endDate = OffsetDateTime.now().plusDays(25);

        var transfersPage = service.findByDate(startDate, endDate, Pageable.unpaged());
        Assertions.assertThat(transfersPage.getTotalElements()).isEqualTo(2);
    }
}