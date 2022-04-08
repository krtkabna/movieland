package com.wasp.rottenpotatoes.web.exception.handler;

import com.wasp.rottenpotatoes.web.exception.IllegalCurrencyException;
import com.wasp.rottenpotatoes.web.exception.InvalidSortOrderException;
import com.wasp.rottenpotatoes.web.exception.MovieNotFoundException;
import com.wasp.rottenpotatoes.web.exception.entity.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class RottenPotatoesExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleThrowable(Throwable e, HttpServletRequest request) {
        logError(e);
        return buildErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleInvalidSortOrder(InvalidSortOrderException e, HttpServletRequest request) {
        logError(e);
        return buildErrorMessage(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        logError(e);
        return buildErrorMessage("Illegal argument provided for path", HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleMovieNotFound(MovieNotFoundException e, HttpServletRequest request) {
        logError(e);
        return buildErrorMessage(e.getLocalizedMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleIllegalCurrency(IllegalCurrencyException e, HttpServletRequest request) {
        logError(e);
        return buildErrorMessage("Illegal currency provided", HttpStatus.BAD_REQUEST, request);
    }

    private void logError(Throwable e) {
        log.error(e.getClass().getSimpleName() + ": " + e.getLocalizedMessage(), e);
    }

    private ErrorMessage buildErrorMessage(String message, HttpStatus status, HttpServletRequest request) {
        return ErrorMessage.builder()
            .timestamp(LocalDateTime.now())
            .error(message)
            .status(status.value())
            .path(request.getServletPath())
            .build();
    }
}
