package com.ywalakamar.crud.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(final String message) {
        super(message);
    }
}
