package com.seyan.reviewmonolith.comment.dto;

import com.seyan.reviewmonolith.filmList.Privacy;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CommentCreationDTO(
        @NotNull(message = "Content should not be null")
        String content,
        @NotNull(message = "User id should not be null")
        Long userId,
        @NotNull(message = "Post id should not be null")
        Long postId,
        @NotNull(message = "Comment date should not be null")
        LocalDateTime commentDate
) {
}
