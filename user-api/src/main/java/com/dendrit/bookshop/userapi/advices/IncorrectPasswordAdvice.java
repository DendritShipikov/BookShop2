package com.dendrit.bookshop.userapi.advices;

import com.dendrit.bookshop.userapi.exceptions.IncorrectPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IncorrectPasswordAdvice {

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String incorrectPasswordHandler(IncorrectPasswordException exception) {
        return exception.getMessage();
    }

}
