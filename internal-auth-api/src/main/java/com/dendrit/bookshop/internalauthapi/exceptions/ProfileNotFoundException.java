package com.dendrit.bookshop.internalauthapi.exceptions;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException() {
        super();
    }

    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
