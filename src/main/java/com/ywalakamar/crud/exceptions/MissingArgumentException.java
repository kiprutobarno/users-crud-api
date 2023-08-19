package com.ywalakamar.crud.exceptions;

public class MissingArgumentException extends RuntimeException {
    public MissingArgumentException(final String message) {
        super(message);
    }
}
