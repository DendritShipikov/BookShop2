package com.dendrit.bookshop.bookapi.exceptions;

public class IllegalBookCountException extends RuntimeException {

    public IllegalBookCountException() {
        super();
    }

    public IllegalBookCountException(String message) {
        super(message);
    }

    public IllegalBookCountException(String message, Throwable cause) {
        super(message, cause);
    }

}
