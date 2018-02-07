package org.spauk.weatheralert.common.rest;

import org.spauk.weatheralert.common.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Common exception interceptor converting known exceptions into correct HTTP codes.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 404 handler
     */
    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<?> notFound(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                                       ex.getMessage(),
                                       getHeadersWithContentType(MediaType.TEXT_PLAIN),
                                       HttpStatus.NOT_FOUND, request);
    }

    /**
     * 500 handler
     */
    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<?> internalError(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                                       ex.getMessage(),
                                       getHeadersWithContentType(MediaType.TEXT_PLAIN),
                                       HttpStatus.INTERNAL_SERVER_ERROR,
                                       request);
    }

    private HttpHeaders getHeadersWithContentType(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return headers;
    }
}