package com.jonasesteves.transfer_scheduling.service;

import com.jonasesteves.transfer_scheduling.entity.Transfer;
import com.jonasesteves.transfer_scheduling.entity.TransferInputTestDataBuilder;
import com.jonasesteves.transfer_scheduling.entity.TransferTestDataBuilder;
import com.jonasesteves.transfer_scheduling.rest.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.rest.exception.MinimalTransferValueException;
import com.jonasesteves.transfer_scheduling.rest.exception.PastScheduledDateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

class TransferServiceTest {

    @ParameterizedTest(name = "calculateFee for amount={0} scheduled in {1} days returns {2}")
    @CsvSource({
            "2.5, 0, 3.08",
            "500, 0, 18.00",
            "1000, 0, 33.00",
            "1500, 0, 0.00",
            "1000.50, 1, 90.04",
            "1000.50, 5, 90.04",
            "1100, 5, 99.00",
            "1100, 11, 0.00",
            "2000, 10, 180.00",
            "2001, 10, 0.00",
            "2001, 11, 164.08",
            "4000, 30, 276.00",
            "4000, 31, 188.00",
            "4000, 41, 68.00",
    })
    void givenTransferUpTo1000ForToday_shouldCalculateFeeCorrectly(String amount, String days, String expectedFee) {
        Transfer transfer = TransferTestDataBuilder.someTransfer()
                .amount(new BigDecimal(amount))
                .scheduledAt(OffsetDateTime.now().plusDays(Long.parseLong(days)))
                .build();

        TransferService transferService = new TransferService(null);
        BigDecimal fee = transferService.calculateFee(transfer);

        Assertions.assertThat(fee).isEqualByComparingTo(new BigDecimal(expectedFee));
    }

    @Test
    void givenTransferBelow1_whenCalculateFee_shouldThrowException() {
        TransferInput transfer = TransferInputTestDataBuilder.someTransferInput()
                .amount(new BigDecimal("0.50"))
                .build();

        TransferService transferService = new TransferService(null);
        Assertions.assertThatExceptionOfType(MinimalTransferValueException.class).isThrownBy(
                () -> transferService.save(transfer)
        );
    }

    @Test
    void givenTransferWithPastScheduledDate_whenSave_shouldThrowException() {
        TransferInput transfer = TransferInputTestDataBuilder.someTransferInput()
                .scheduledAt(OffsetDateTime.now().minusDays(1))
                .build();

        TransferService transferService = new TransferService(null);
        Assertions.assertThatExceptionOfType(PastScheduledDateException.class).isThrownBy(
                () -> transferService.save(transfer)
        );
    }
}