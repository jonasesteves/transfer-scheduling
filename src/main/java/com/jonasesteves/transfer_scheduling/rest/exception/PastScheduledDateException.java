package com.jonasesteves.transfer_scheduling.rest.exception;

public class PastScheduledDateException extends RuntimeException {
    public PastScheduledDateException(String message) {
        super(message);
    }
}
