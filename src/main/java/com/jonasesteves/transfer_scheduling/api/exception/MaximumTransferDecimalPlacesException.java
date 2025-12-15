package com.jonasesteves.transfer_scheduling.api.exception;

public class MaximumTransferDecimalPlacesException extends InvalidAmountException {

    public MaximumTransferDecimalPlacesException() {
        super("amount must have at most 2 decimal places");
    }
}
