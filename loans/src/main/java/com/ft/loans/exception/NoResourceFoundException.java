package com.ft.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND)
public class NoResourceFoundException extends RuntimeException {
    public NoResourceFoundException( String msg) {
        super(msg);
    }
}
