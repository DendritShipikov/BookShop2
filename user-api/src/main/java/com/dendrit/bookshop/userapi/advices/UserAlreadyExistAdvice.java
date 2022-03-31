package com.dendrit.bookshop.userapi.advices;

import com.dendrit.bookshop.userapi.exceptions.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAlreadyExistAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String userAlreadyExistHandler(UserAlreadyExistException exception) {
        return exception.getMessage();
    }

}
