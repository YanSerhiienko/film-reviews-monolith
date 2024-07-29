package com.seyan.reviewmonolith.comment;

public enum PostType {
    REVIEW("Review"),
    LIST("List");

    public final String label;

    PostType(String label) {
        this.label = label;
    }
}
