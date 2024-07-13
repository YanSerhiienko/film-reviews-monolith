package com.seyan.reviewmonolith.profile.dto;

import com.seyan.reviewmonolith.film.Film;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProfileUpdateDTO(
        //@NotNull
        //Long id,
        String name,
        String biography,
        List<Long> starringFilmsId,
        List<Long> directedFilmsId
) {
}
