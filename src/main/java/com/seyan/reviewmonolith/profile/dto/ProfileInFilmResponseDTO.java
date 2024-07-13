package com.seyan.reviewmonolith.profile.dto;

import jakarta.validation.constraints.NotNull;

public record ProfileInFilmResponseDTO(
        Long id,
        String name
) {

}
