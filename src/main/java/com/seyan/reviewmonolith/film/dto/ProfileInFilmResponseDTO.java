package com.seyan.reviewmonolith.film.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public record ProfileInFilmResponseDTO(
        Long id,
        String name,
        String url
) {

}
