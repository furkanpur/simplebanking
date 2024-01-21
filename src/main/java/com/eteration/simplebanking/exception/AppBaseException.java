package com.eteration.simplebanking.exception;

public class AppBaseException extends RuntimeException {
    protected String message;
    protected int code;

    public AppBaseException(String message, int code) {
        super(message);

        this.message = message;
        this.code = code;
    }
}
