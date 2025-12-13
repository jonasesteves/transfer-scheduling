package com.jonasesteves.transfer_scheduling.service;

import com.jonasesteves.transfer_scheduling.entity.Transfer;
import com.jonasesteves.transfer_scheduling.repository.TransferRepository;
import com.jonasesteves.transfer_scheduling.rest.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.rest.dto.TransferOutput;
import com.jonasesteves.transfer_scheduling.rest.exception.MinimalTransferValueException;
import com.jonasesteves.transfer_scheduling.rest.exception.PastScheduledDateException;
import com.jonasesteves.transfer_scheduling.utility.IdGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
public class TransferService {

    private static final BigDecimal MINIMAL_TRANSFER_AMOUNT = new BigDecimal("1.00");
    private static final BigDecimal FEE_FOR_TODAY = new BigDecimal("0.03");
    private static final BigDecimal FEE_FOR_10_DAYS = new BigDecimal("0.09");
    private static final BigDecimal FEE_FOR_20_DAYS = new BigDecimal("0.082");
    private static final BigDecimal FEE_FOR_30_DAYS = new BigDecimal("0.069");
    private static final BigDecimal FEE_FOR_40_DAYS = new BigDecimal("0.047");
    private static final BigDecimal FEE_FOR_MORE_THAN_40_DAYS = new BigDecimal("0.017");
    private static final BigDecimal FIXED_FEE = new BigDecimal("3.00");
    private static final ZoneId BANK_ZONE = ZoneId.of("Europe/Lisbon");

    private final TransferRepository repository;

    public TransferService(TransferRepository repository) {
        this.repository = repository;
    }

    public TransferOutput save(TransferInput transferInput) {
        validateScheduledAt(transferInput.getScheduledAt());
        validateTransferAmount(transferInput.getAmount());

        Transfer transfer = Transfer.builder()
                .id(IdGenerator.generateTimeBasedUUID())
                .beneficiary(transferInput.getBeneficiary())
                .amount(transferInput.getAmount())
                .feeAmount(BigDecimal.ZERO)
                .scheduledAt(transferInput.getScheduledAt())
                .build();

        BigDecimal fee = calculateFee(transfer);
        transfer.setFeeAmount(fee);

        Transfer savedTransfer = repository.save(transfer);
        return toTransferOutput(savedTransfer);
    }

    BigDecimal calculateFee(Transfer transfer) {
        long days = totalDaysFromNow(transfer.getScheduledAt());

        if (transfer.getAmount().compareTo(new BigDecimal("1000")) <= 0 && days == 0) {
            return roundToTwoDecimalPlaces(transfer.getAmount().multiply(FEE_FOR_TODAY).add(FIXED_FEE));
        }

        if (transfer.getAmount().compareTo(new BigDecimal("2000")) <= 0 && days >= 1 && days <= 10) {
            return roundToTwoDecimalPlaces(transfer.getAmount().multiply(FEE_FOR_10_DAYS));
        }

        if (transfer.getAmount().compareTo(new BigDecimal("2000")) > 0) {
            if (days >= 11 && days <= 20) {
                return roundToTwoDecimalPlaces(transfer.getAmount().multiply(FEE_FOR_20_DAYS));
            } else if (days >= 21 && days <= 30) {
                return roundToTwoDecimalPlaces(transfer.getAmount().multiply(FEE_FOR_30_DAYS));
            } else if (days >= 31 && days <= 40) {
                return roundToTwoDecimalPlaces(transfer.getAmount().multiply(FEE_FOR_40_DAYS));
            } else if (days > 40) {
                return roundToTwoDecimalPlaces(transfer.getAmount().multiply(FEE_FOR_MORE_THAN_40_DAYS));
            }
        }

        return BigDecimal.ZERO;
    }

    private static void validateTransferAmount(BigDecimal amount) {
        if (amount.compareTo(MINIMAL_TRANSFER_AMOUNT) < 0) {
            throw new MinimalTransferValueException("amount must be at least 1.00");
        }
    }

    private static void validateScheduledAt(OffsetDateTime scheduledAt) {
        LocalDate scheduledDate = scheduledAt.atZoneSameInstant(BANK_ZONE).toLocalDate();
        LocalDate currentDate = LocalDate.now(BANK_ZONE);

        if (scheduledDate.isBefore(currentDate)) {
            throw new PastScheduledDateException("scheduledAt cannot be in the past");
        }
    }

    private static long totalDaysFromNow(OffsetDateTime scheduledAt) {
        return ChronoUnit.DAYS.between(LocalDate.now(), scheduledAt.toLocalDate());
    }

    private static BigDecimal roundToTwoDecimalPlaces(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    private static TransferOutput toTransferOutput(Transfer transfer) {
        return TransferOutput.builder()
                .id(transfer.getId())
                .beneficiary(transfer.getBeneficiary())
                .amount(transfer.getAmount())
                .feeAmount(transfer.getFeeAmount())
                .scheduledAt(transfer.getScheduledAt())
                .build();
    }
}
