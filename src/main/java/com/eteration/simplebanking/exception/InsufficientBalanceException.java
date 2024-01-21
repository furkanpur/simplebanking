package com.eteration.simplebanking.exception;


import com.eteration.simplebanking.exception.AppBaseException;

// This class is a place holder you can change the complete implementation
public class InsufficientBalanceException extends AppBaseException {
    public InsufficientBalanceException(String message) {
        super(message, 1001);
    }
}
