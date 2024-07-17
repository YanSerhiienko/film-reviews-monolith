package com.seyan.reviewmonolith.exception.film;

import java.io.Serial;

public class ActivityDeleteException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 8;

    public ActivityDeleteException(String message) {
        super(message);
    }
}
