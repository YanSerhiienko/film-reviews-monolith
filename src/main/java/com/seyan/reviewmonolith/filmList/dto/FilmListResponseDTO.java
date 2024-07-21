package com.seyan.reviewmonolith.filmList.dto;

import java.util.LinkedHashSet;
import java.util.List;

public record FilmListResponseDTO(
        Long id,
        Long userId,
        String title,
        String description,
        Long likeCount,
        Long commentCount,
        List<FilmInFilmListResponseDTO> filmIds
) {

}
