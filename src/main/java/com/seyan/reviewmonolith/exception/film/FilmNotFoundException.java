package com.seyan.reviewmonolith.exception.film;

public class FilmNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 4;

    public FilmNotFoundException(String message) {
        super(message);
    }
}
