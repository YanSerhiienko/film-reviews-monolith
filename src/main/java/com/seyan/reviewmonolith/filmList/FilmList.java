package com.seyan.reviewmonolith.filmList;

import com.seyan.reviewmonolith.filmList.entry.ListEntry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "list_id", referencedColumnName = "id")
    private List<ListEntry> filmIds = new ArrayList<>();

    //private HashSet<Long> commentIds;
    //private Map<Integer, ListEntry> filmIds;

    //@Builder.Default
    //@OrderColumn()
/*    @JoinColumn(name = "list_id", referencedColumnName = "id")
    @OneToMany(cascade=CascadeType.ALL)
    private List<ListEntry> filmIds = new ArrayList<>();*/

    /*@OneToMany(cascade=CascadeType.ALL)
    //@JoinColumn(name = "list_id", referencedColumnName = "id")
    private Map<Integer, Long> filmIds;*/

    //todo TRY WITH ORDERED ONW MORE TIME

    private LocalDate creationDate;

    private LocalDate lastUpdateDate;
    //todo you watched method

    public FilmList() {
        this.likeCount = 0L;
        this.commentCount = 0L;
    }
}
