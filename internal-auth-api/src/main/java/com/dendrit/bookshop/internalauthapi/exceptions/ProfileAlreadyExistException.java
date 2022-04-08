package com.dendrit.bookshop.internalauthapi.exceptions;

public class ProfileAlreadyExistException extends RuntimeException {

    public ProfileAlreadyExistException() {
        super();
    }

    public ProfileAlreadyExistException(String message) {
        super(message);
    }

    public ProfileAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
