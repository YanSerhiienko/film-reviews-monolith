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
public record FilmUpdateDTO(
        @NotNull(message = "Film id is required")
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        Profile director,
        //List<Profile> castProfiles;
        List<Long> castProfiles,
        List<Genre> genres,
        LocalTime runningTime
) {

}
