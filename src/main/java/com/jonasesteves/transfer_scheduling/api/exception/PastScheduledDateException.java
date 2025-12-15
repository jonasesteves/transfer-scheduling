package com.jonasesteves.transfer_scheduling.api.exception;

public class PastScheduledDateException extends RuntimeException {

    public PastScheduledDateException() {
        super("scheduledAt cannot be in the past");
    }
}
