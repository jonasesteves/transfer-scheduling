package com.jonasesteves.transfer_scheduling.domain.service;

import com.jonasesteves.transfer_scheduling.api.assembler.TransferAssembler;
import com.jonasesteves.transfer_scheduling.api.assembler.TransferDisassembler;
import com.jonasesteves.transfer_scheduling.api.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.api.dto.TransferOutput;
import com.jonasesteves.transfer_scheduling.api.exception.MaximumTransferDecimalPlacesException;
import com.jonasesteves.transfer_scheduling.api.exception.MinimalTransferAmountException;
import com.jonasesteves.transfer_scheduling.api.exception.PastScheduledDateException;
import com.jonasesteves.transfer_scheduling.api.exception.StartDateGreaterThanFinalDateException;
import com.jonasesteves.transfer_scheduling.api.exception.TransferNotFoundException;
import com.jonasesteves.transfer_scheduling.domain.entity.Transfer;
import com.jonasesteves.transfer_scheduling.domain.repository.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

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
    private final TransferAssembler assembler;
    private final TransferDisassembler disassembler;

    public TransferService(TransferRepository repository, TransferAssembler assembler, TransferDisassembler disassembler) {
        this.repository = repository;
        this.assembler = assembler;
        this.disassembler = disassembler;
    }

    public TransferOutput save(TransferInput transferInput) {
        validateScheduledAt(transferInput.getScheduledAt());
        validateTransferAmount(transferInput.getAmount());

        Transfer transfer = disassembler.toDomainModel(transferInput);

        BigDecimal fee = calculateFee(transfer);
        transfer.setFeeAmount(fee);

        Transfer savedTransfer = repository.saveAndFlush(transfer);
        return assembler.toTransferOutput(savedTransfer);
    }

    public TransferOutput update(UUID id, TransferInput transferInput) {
        Transfer existingTransfer = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        validateScheduledAt(transferInput.getScheduledAt());
        validateTransferAmount(transferInput.getAmount());

        existingTransfer.setAmount(transferInput.getAmount());
        existingTransfer.setBeneficiary(transferInput.getBeneficiary());
        existingTransfer.setScheduledAt(transferInput.getScheduledAt());

        BigDecimal fee = calculateFee(existingTransfer);
        existingTransfer.setFeeAmount(fee);

        existingTransfer = repository.saveAndFlush(existingTransfer);
        return assembler.toTransferOutput(existingTransfer);
    }

    public Page<TransferOutput> findAll(Pageable pageable) {
        Page<Transfer> transfers = repository.findAll(pageable);
        return transfers.map(assembler::toTransferOutput);
    }

    public TransferOutput findById(UUID id) {
        Transfer transfer = repository.findById(id)
                .orElseThrow(() -> new TransferNotFoundException(id));
        return assembler.toTransferOutput(transfer);
    }

    public Page<TransferOutput> findByDate(OffsetDateTime startDate, OffsetDateTime endDate, Pageable pageable) {
        if (endDate.isBefore(startDate)) {
            throw new StartDateGreaterThanFinalDateException();
        }

        Page<Transfer> transfers = repository.findBySchedulingDate(startDate, endDate, pageable);
        return transfers.map(assembler::toTransferOutput);
    }

    public void delete(UUID id) {
        Transfer transfer = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(transfer);
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
        if (amount.scale() > 2) {
            throw new MaximumTransferDecimalPlacesException();
        }

        if (amount.compareTo(MINIMAL_TRANSFER_AMOUNT) < 0) {
            throw new MinimalTransferAmountException();
        }
    }

    private static void validateScheduledAt(OffsetDateTime scheduledAt) {
        LocalDate scheduledDate = scheduledAt.atZoneSameInstant(BANK_ZONE).toLocalDate();
        LocalDate currentDate = LocalDate.now(BANK_ZONE);

        if (scheduledDate.isBefore(currentDate)) {
            throw new PastScheduledDateException();
        }
    }

    private static long totalDaysFromNow(OffsetDateTime scheduledAt) {
        return ChronoUnit.DAYS.between(LocalDate.now(), scheduledAt.toLocalDate());
    }

    private static BigDecimal roundToTwoDecimalPlaces(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }
}
