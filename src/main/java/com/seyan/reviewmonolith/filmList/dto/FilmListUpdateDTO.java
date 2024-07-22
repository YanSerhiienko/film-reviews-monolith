package com.seyan.reviewmonolith.filmList.dto;

import com.seyan.reviewmonolith.filmList.Privacy;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public record FilmListUpdateDTO(
        //@NotNull
        //Long id,
        //Long userId,
        String title,
        String description,
        Privacy privacy,
        Map<Integer, Long> filmIds
) {
}
