package com.seyan.reviewmonolith.filmList.dto;

import java.util.LinkedHashSet;

public record FilmListCreationDTO(
        Long userId,
        String title,
        String description,
        LinkedHashSet<Long>filmIds
) {

}
