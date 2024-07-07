package com.seyan.reviewmonolith.film;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "films")
@DynamicUpdate
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Date releaseDate;
    private Double rating;
    private Profile director;
    //private List<Profile> cast;
    private List<Long> cast;
    private List<Genre> genres;
    private Time runningTime;
    private Long watchedCount;
    private Long listCount;
    private Long likeCount;
}
