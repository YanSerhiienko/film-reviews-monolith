package com.seyan.reviewmonolith.film.dto;

import com.seyan.reviewmonolith.film.Genre;
import com.seyan.reviewmonolith.film.Profile;
import lombok.Getter;

import java.sql.Time;
import java.util.List;

@Getter
public record FilmUpdateDTO(
        Long id,
        String title,
        String description,
        String releaseYear,
        Profile director,
        //private List<Profile> cast;
        List<Long> cast,
        List<Genre> genres,
        Time runningTime
) {

}
