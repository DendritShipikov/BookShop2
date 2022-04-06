package com.dendrit.bookshop.bookapi.advices;

import com.dendrit.bookshop.bookapi.exceptions.IllegalBookCountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IllegalBookCountAdvice {

    @ExceptionHandler(IllegalBookCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalBookCountHandler(IllegalBookCountException exception) {
        return exception.getMessage();
    }

}
