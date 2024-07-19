package com.seyan.reviewmonolith.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Entity
//@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Date creationDate;
    private Long authorId;
    //private Boolean isReviewComment;
    //private Long likeCount;
    //private List<Long> likedUsersIds;
}
