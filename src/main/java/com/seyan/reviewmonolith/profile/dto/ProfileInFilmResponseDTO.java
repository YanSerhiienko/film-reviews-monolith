package com.seyan.reviewmonolith.profile.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record ProfileInFilmResponseDTO(
        Long id,
        String name,
        String url
) {

}
