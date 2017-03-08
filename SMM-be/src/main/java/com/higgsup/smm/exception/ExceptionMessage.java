package com.higgsup.smm.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
    INVALID_TOKEN("Token is invalid", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED("Token is expired", HttpStatus.FORBIDDEN),
    CONTACT_NOT_FOUND_EXCEPTION("Contact not found", HttpStatus.BAD_REQUEST);

    private String message;
    private HttpStatus httpStatus;


    ExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
