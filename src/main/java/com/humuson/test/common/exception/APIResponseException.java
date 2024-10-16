package com.humuson.test.common.exception;

import lombok.Getter;

@Getter
public class APIResponseException extends RuntimeException {

    private final int statusCode;

    public APIResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public APIResponseException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

}
