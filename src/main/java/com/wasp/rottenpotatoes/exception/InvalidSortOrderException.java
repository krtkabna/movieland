package com.wasp.rottenpotatoes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) //TODO ask which exception handler fits better (I think this one because json)
public class InvalidSortOrderException extends RuntimeException {
    public InvalidSortOrderException(String message) {
        super(message);
    }

    public InvalidSortOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
