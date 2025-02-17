package com.ft.accounts.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends Exception {

    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
