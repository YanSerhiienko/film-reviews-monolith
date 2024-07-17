package com.seyan.reviewmonolith.exception.film;

import java.io.Serial;

public class ActivityNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7;

    public ActivityNotFoundException(String message) {
        super(message);
    }
}
