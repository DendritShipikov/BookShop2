package com.dendrit.bookshop.bookapi.advices;

import com.dendrit.bookshop.bookapi.exceptions.UserHasNoAuthorityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserHasNoAuthorityAdvice {

    @ExceptionHandler(UserHasNoAuthorityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String userHasNoAuthorityHandler(UserHasNoAuthorityException exception) {
        return exception.getMessage();
    }
}
