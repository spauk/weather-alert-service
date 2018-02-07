package org.spauk.weatheralert.common.exception;

/**
 * Common exception for a given resource not found.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}