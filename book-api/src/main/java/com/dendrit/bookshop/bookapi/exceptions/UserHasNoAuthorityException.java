package com.dendrit.bookshop.bookapi.exceptions;

public class UserHasNoAuthorityException extends RuntimeException {

    public UserHasNoAuthorityException() {
        super();
    }

    public UserHasNoAuthorityException(String message) {
        super(message);
    }

    public UserHasNoAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

}
