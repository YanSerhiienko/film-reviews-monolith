package com.seyan.reviewmonolith.exception.user;

import java.io.Serial;

public class EmailAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 3;

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
