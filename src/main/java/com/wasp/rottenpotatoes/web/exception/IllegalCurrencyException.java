package com.wasp.rottenpotatoes.web.exception;

public class IllegalCurrencyException extends RuntimeException {
    public IllegalCurrencyException(String message) {
        super(message);
    }

    public IllegalCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
