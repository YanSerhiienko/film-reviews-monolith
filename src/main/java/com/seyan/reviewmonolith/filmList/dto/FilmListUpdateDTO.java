package com.seyan.reviewmonolith.filmList.dto;

import java.util.LinkedHashSet;

public record FilmListUpdateDTO(
        //@NotNull
        //Long id,
        Long userId,
        String title,
        String description,
        LinkedHashSet<Long> filmIds
) {
}
