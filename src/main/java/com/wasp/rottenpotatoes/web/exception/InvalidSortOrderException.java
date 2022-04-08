package com.wasp.rottenpotatoes.web.exception;

public class InvalidSortOrderException extends RuntimeException {
    public InvalidSortOrderException(String message) {
        super(message);
    }
}
