package com.test.config.exception;

import java.io.Serial;

public class DataNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1529128907688752733L;

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
