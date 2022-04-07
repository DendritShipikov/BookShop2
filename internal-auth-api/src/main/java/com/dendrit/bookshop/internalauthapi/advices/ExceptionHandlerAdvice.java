package com.dendrit.bookshop.internalauthapi.advices;

import com.dendrit.bookshop.internalauthapi.exceptions.IncorrectPasswordException;
import com.dendrit.bookshop.internalauthapi.exceptions.ProfileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String incorrectPasswordHandler(IncorrectPasswordException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String profileNotFoundHandler(ProfileNotFoundException exception) {
        return exception.getMessage();
    }

}
