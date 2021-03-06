package com.dendrit.bookshop.common.jwt.exceptions;

public class BadJwtException extends RuntimeException {

    public BadJwtException() {
        super();
    }

    public BadJwtException(String message) {
        super(message);
    }

    public BadJwtException(String message, Throwable cause) {
        super(message, cause);
    }

}
