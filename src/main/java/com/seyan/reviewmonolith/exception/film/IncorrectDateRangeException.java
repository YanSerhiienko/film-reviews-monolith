package com.seyan.reviewmonolith.exception.film;

public class IncorrectDateRangeException extends RuntimeException {
    private static final long serialVersionUID = 2;

    public IncorrectDateRangeException(String message) {
        super(message);
    }
}
