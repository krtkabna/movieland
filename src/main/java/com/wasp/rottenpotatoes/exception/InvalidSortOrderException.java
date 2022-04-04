package com.wasp.rottenpotatoes.exception;

public class InvalidSortOrderException extends RuntimeException {
    public InvalidSortOrderException(String message) {
        super(message);
    }

    public InvalidSortOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
