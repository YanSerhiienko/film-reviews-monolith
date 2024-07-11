package com.seyan.reviewmonolith.exception.profile;

import java.io.Serial;

public class ProfileNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6;

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
