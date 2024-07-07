package com.seyan.reviewmonolith.film.dto;

import com.seyan.reviewmonolith.film.Genre;
import com.seyan.reviewmonolith.film.Profile;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Time;
import java.util.List;

@Getter
public record FilmCreationDTO(
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
