package com.seyan.reviewmonolith.exception.film;

import java.io.Serial;

public class FilmNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 4;

    public FilmNotFoundException(String message) {
        super(message);
    }
}
