package com.seyan.reviewmonolith.filmList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Builder
@Entity
@Table(name = "film_list")
public class FilmList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Privacy privacy;
    private Long likeCount;
    private Long commentCount;
    //private HashSet<Long> commentIds;
    private LinkedHashSet<Long> filmIds;
    //todo you watched method

    public FilmList() {
        this.likeCount = 0L;
        this.commentCount = 0L;
    }
}
