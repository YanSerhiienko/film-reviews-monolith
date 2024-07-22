package com.seyan.reviewmonolith.profile.dto;

import com.seyan.reviewmonolith.film.Film;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProfileCreationDTO(
        @NotNull(message = "Profile name is required")
        String name,
        @NotNull(message = "Profile biography is required")
        String biography,
        List<Long> starringFilmsId,
        List<Long> directedFilmsId
) {

}
