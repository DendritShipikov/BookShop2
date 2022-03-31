package com.dendrit.bookshop.bookapi.advices;

import com.dendrit.bookshop.common.jwt.exceptions.BadJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadJwtAdvice {

    @ExceptionHandler(BadJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String badJwtHandler(BadJwtException exception) {
        return exception.getMessage();
    }

}
