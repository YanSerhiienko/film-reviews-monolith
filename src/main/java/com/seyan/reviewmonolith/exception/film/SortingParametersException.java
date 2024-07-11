package com.seyan.reviewmonolith.exception.film;

import java.io.Serial;

public class SortingParametersException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 5;

    public SortingParametersException(String message) {
        super(message);
    }
}
