package com.jonasesteves.transfer_scheduling.api.exception;

public class MinimalTransferAmountException extends InvalidAmountException {

    public MinimalTransferAmountException() {
        super("amount must be at least 1.00");
    }
}
