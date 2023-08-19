package com.ywalakamar.crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ywalakamar.crud.response.Response;
import com.ywalakamar.crud.utils.StatusCode;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Response handleRecordNotfoundException(RecordNotFoundException exception) {
        return new Response(false, StatusCode.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MissingArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Response handleMissingArgumentException(MissingArgumentException exception) {
        return new Response(false, StatusCode.INVALID_ARGUMENT, exception.getMessage());
    }
}
