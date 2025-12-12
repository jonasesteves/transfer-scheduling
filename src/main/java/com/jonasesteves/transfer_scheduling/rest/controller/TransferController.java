package com.jonasesteves.transfer_scheduling.rest.controller;

import com.jonasesteves.transfer_scheduling.rest.dto.TransferInput;
import com.jonasesteves.transfer_scheduling.rest.dto.TransferOutput;
import com.jonasesteves.transfer_scheduling.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferOutput create(@RequestBody @Valid TransferInput transferInput) {
        return transferService.save(transferInput);
    }
}
