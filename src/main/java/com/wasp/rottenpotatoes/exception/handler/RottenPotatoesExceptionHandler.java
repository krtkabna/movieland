package com.wasp.rottenpotatoes.exception.handler;

import com.wasp.rottenpotatoes.exception.InvalidSortOrderException;
import com.wasp.rottenpotatoes.exception.MovieNotFoundException;
import com.wasp.rottenpotatoes.exception.entity.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RottenPotatoesExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidSortOrder(InvalidSortOrderException e, HttpServletRequest request) {
        return buildErrorMessage(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        return buildErrorMessage(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleMovieNotFound(MovieNotFoundException e, HttpServletRequest request) {
        return buildErrorMessage(e, HttpStatus.NOT_FOUND, request);
    }

    private ErrorMessage buildErrorMessage(Throwable e, HttpStatus status, HttpServletRequest request) {
        return ErrorMessage.builder()
            .timestamp(LocalDateTime.now())
            .error(e.getClass().getSimpleName() + ": " + e.getLocalizedMessage())
            .status(status.value())
            .path(request.getServletPath())
            .build();
    }
}
