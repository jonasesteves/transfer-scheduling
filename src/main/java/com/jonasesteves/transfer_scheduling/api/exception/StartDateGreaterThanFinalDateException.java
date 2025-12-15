package com.jonasesteves.transfer_scheduling.api.exception;

public class StartDateGreaterThanFinalDateException extends RuntimeException {

    public StartDateGreaterThanFinalDateException() {
        super("startDate cannot be greater than endDate");
    }
}
