package com.seyan.reviewmonolith.film;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Genre {
    ACTION ("Action"),
    ADVENTURE ("Adventure"),
    ANIMATION ("Animation"),
    COMEDY ("Comedy"),
    CRIME ("Crime"),
    DOCUMENTARY ("Documentary"),
    DRAMA ("Drama"),
    FAMILY ("Family"),
    FANTASY ("Fantasy"),
    HISTORY ("History"),
    HORROR ("Horror"),
    MUSIC ("Music"),
    MYSTERY ("Mystery"),
    ROMANCE ("Romance"),
    SCIENCE ("Science"),
    FICTION ("Fiction"),
    THRILLER ("Thriller"),
    TV ("TV"),
    MOVIE ("Movie"),
    WAR ("War"),
    WESTERN ("Western");

    private String title;
}
