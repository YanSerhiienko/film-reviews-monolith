package com.seyan.reviewmonolith.filmList;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "entries")
@IdClass(ListEntryId.class)
@DynamicUpdate
public class ListEntry {
    @Id
    @Column(name = "list_id")
    private Long listId;
    @Id
    @Column(name = "film_id")
    private Long filmId;

    /*@EmbeddedId
    private ListEntryId id;*/

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;*/
    //private Long filmId;

    private LocalDate whenAdded;

    /*public ListEntry(Long listId, Long filmId, LocalDate whenAdded) {
        this.whenAdded = whenAdded;
    }*/
}
