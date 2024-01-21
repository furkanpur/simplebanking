package com.eteration.simplebanking.exception;

public class NoAccountFoundException extends AppBaseException {
    public NoAccountFoundException(String message) {
        super(message, 1002);
    }
}
