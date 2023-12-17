package com.abdelrahman.amin.vending.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BuisnessException extends Exception {
    public BuisnessException(String reason) {
        super(reason);
    }
}
