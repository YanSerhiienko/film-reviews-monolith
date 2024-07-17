package com.seyan.reviewmonolith.filmList.customList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "list")
public class CustomList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long authorId;
    private Long likeCount;
    private Long commentCount;
    private List<Long> commentIds;
    private LinkedList<Long> filmIds;
    //todo you watched method
}
