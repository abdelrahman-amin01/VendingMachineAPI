package com.abdelrahman.amin.vending.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAvailableAmountException extends Exception {
    public NotAvailableAmountException(Long id) {
        super("Product with id:" + id + ", Not available Amount.");
    }
}
