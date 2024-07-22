package com.seyan.reviewmonolith.filmList.dto;

import com.seyan.reviewmonolith.filmList.Privacy;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class FilmListResponseDTO {
    Long id;
    Long userId;
    String title;
    String description;
    Privacy privacy;
    Long likeCount;
    Long commentCount;
    List<FilmInFilmListResponseDTO> films;
}
