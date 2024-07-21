package com.seyan.reviewmonolith.filmList;

public enum Privacy {
    PUBLIC("Public"),
    SHARED("Shared"),
    FRIENDS("Friends"),
    PRIVATE("Private");

    public final String label;

    Privacy(String label) {
        this.label = label;
    }
}
