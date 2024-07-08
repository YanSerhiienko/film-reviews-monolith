package com.seyan.reviewmonolith.film.dto;

import com.seyan.reviewmonolith.film.Genre;
import com.seyan.reviewmonolith.film.Profile;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
public record FilmCreationDTO(
        @NotNull(message = "Film title is required")
        String title,
        @NotNull(message = "Film description is required")
        String description,
        @NotNull(message = "Film release date is required")
        LocalDate releaseDate,
        @NotNull(message = "Film director is required")
        Profile director,
        @NotNull(message = "Film cast profiles is required")
        //List<Profile> castProfiles;
        List<Long> castProfiles,
        @NotNull(message = "Film genres is required")
        List<Genre> genres,
        @NotNull(message = "Film running time is required")
        LocalTime runningTime
) {

}
