package com.tr.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BaseValidationException extends BaseException {
    public BaseValidationException(String message) {
        super(message);
    }
}
