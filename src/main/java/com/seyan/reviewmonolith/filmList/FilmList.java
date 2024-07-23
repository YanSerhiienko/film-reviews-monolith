package com.seyan.reviewmonolith.filmList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.*;

@ToString
@Data
@AllArgsConstructor
//@NoArgsConstructor
//@Builder
@Entity
@Table(name = "lists")
@DynamicUpdate
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
    //private Map<Integer, ListEntry> filmIds;

    /*@Builder.Default
    @OrderColumn(updatable = true)
    @OneToMany(cascade=CascadeType.ALL)
    private List<ListEntry> entries = new ArrayList<>();*/

    @OneToMany(cascade=CascadeType.ALL)
    //@JoinColumn(name = "list_id", referencedColumnName = "id")
    private Map<Integer, ListEntry> entries;

    private LocalDate creationDate;

    private LocalDate lastUpdateDate;
    //todo you watched method

    public FilmList() {
        this.likeCount = 0L;
        this.commentCount = 0L;
    }
}
