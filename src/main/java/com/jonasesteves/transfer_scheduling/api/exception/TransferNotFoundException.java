package com.jonasesteves.transfer_scheduling.api.exception;

import java.util.UUID;

public class TransferNotFoundException extends EntityNotFoundException {

    public TransferNotFoundException(String message) {
        super(message);
    }

    public TransferNotFoundException(UUID id) {
        this(String.format("There is no transfer with code %s", id));
    }
}
