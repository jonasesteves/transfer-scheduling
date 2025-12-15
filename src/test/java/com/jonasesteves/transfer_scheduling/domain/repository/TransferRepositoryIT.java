package com.jonasesteves.transfer_scheduling.domain.repository;

import com.jonasesteves.transfer_scheduling.api.config.SpringDataAuditingConfig;
import com.jonasesteves.transfer_scheduling.domain.entity.Transfer;
import com.jonasesteves.transfer_scheduling.domain.entity.TransferTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@DataJpaTest
@Import(SpringDataAuditingConfig.class)
class TransferRepositoryIT {

    private final TransferRepository repository;

    @Autowired
    TransferRepositoryIT(TransferRepository repository) {
        this.repository = repository;
    }

    @Test
    void shouldSaveAndFindTransfer() {
        Transfer transfer = TransferTestDataBuilder.someTransfer().build();
        UUID transferId = transfer.getId();

        Transfer savedTransfer = repository.saveAndFlush(transfer);

        Assertions.assertThat(savedTransfer.getAmount()).isEqualTo(transfer.getAmount());

        Transfer foundTransfer = repository.findById(transferId).orElseThrow();

        Assertions.assertThat(foundTransfer).satisfies(
                t -> Assertions.assertThat(t.getId()).isEqualTo(transferId),
                t -> Assertions.assertThat(t.getAmount()).isEqualByComparingTo(transfer.getAmount()),
                t -> Assertions.assertThat(t.getScheduledAt()).isEqualTo(transfer.getScheduledAt()),
                t -> Assertions.assertThat(t.getFeeAmount()).isEqualByComparingTo(transfer.getFeeAmount())
        );
    }

    @Test
    void shouldSetAuditingValue() {
        Transfer transfer = TransferTestDataBuilder.someTransfer().build();
        transfer = repository.saveAndFlush(transfer);

        Assertions.assertWith(transfer,
                t -> Assertions.assertThat(t.getCreatedByUserId()).isNotNull(),
                t -> Assertions.assertThat(t.getCreatedAt()).isNotNull(),
                t -> Assertions.assertThat(t.getLastModifiedByUserId()).isNotNull(),
                t -> Assertions.assertThat(t.getLastModifiedAt()).isNotNull()
        );
    }
}