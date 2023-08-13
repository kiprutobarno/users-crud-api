package com.ywalakamar.crud.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException(final String message) {
        super(message);
    }
}
