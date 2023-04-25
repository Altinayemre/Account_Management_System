package com.tr.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ServiceOperationException {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends BaseException{
        public NotFoundException(String message) {
            super(message);
        }
    }
}
