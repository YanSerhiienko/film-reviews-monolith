package com.seyan.reviewmonolith.profile.dto;

import com.seyan.reviewmonolith.film.Film;
import com.seyan.reviewmonolith.film.dto.FilmInProfileResponseDTO;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public record ProfileResponseDTO(
        Long id,
        String name,
        String biography,
        List<FilmInProfileResponseDTO> starringFilms,
        List<FilmInProfileResponseDTO> directedFilms
) {

}
