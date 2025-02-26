package com.test.config.exception;

import java.io.Serial;

/**
 * 이미 데이터가 존재하는 경우 예외
 */
public class ConflictException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -3272087119569179942L;

    public ConflictException() {
    }

    public ConflictException(String message) {
        super(message);
    }
}
