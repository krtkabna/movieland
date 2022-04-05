package com.wasp.rottenpotatoes.exception.handler;

import com.wasp.rottenpotatoes.exception.InvalidSortOrderException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice //TODO ask which exception handler fits better
public class RottenPotatoesExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidSortOrderException.class})
    protected ResponseEntity<Object> handleSortOrderBadRequest(RuntimeException ex, WebRequest request) {
        String responseBody = ex.getLocalizedMessage();
        return handleExceptionInternal(ex, responseBody,
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
