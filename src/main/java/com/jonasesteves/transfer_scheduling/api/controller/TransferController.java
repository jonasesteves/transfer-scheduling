package com.jonasesteves.transfer_scheduling.api.controller;

import com.jonasesteves.transfer_scheduling.api.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.api.dto.TransferOutput;
import com.jonasesteves.transfer_scheduling.domain.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService service;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferOutput create(@RequestBody @Valid TransferInput transferInput) {
        return service.save(transferInput);
    }

    @GetMapping("/{id}")
    public TransferOutput findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping(params = {"startDate", "endDate"})
    public Page<TransferOutput> findByDate(
            @RequestParam OffsetDateTime startDate,
            @RequestParam OffsetDateTime endDate,
            @PageableDefault Pageable pageable) {
        return service.findByDate(startDate, endDate, pageable);
    }

    @GetMapping
    public Page<TransferOutput> findAll(@PageableDefault Pageable pageable) {
        return service.findAll(pageable);
    }

    @PutMapping("/{id}")
    public TransferOutput update(@PathVariable UUID id, @RequestBody @Valid TransferInput transferInput) {
        return service.update(id, transferInput);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
