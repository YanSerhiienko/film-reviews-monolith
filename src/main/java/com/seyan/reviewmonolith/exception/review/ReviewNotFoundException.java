package com.seyan.reviewmonolith.exception.review;

import java.io.Serial;

public class ReviewNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7;

    public ReviewNotFoundException(String message) {
        super(message);
    }
}
