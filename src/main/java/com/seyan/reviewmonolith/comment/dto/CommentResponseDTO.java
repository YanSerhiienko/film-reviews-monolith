package com.seyan.reviewmonolith.comment.dto;

import com.seyan.reviewmonolith.comment.PostType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String content,
        Long userId,
        LocalDateTime commentDate,
        Boolean isEdited
) {
}
