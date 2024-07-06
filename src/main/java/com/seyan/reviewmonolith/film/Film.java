package com.seyan.reviewmonolith.film;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "films")
public class Film {
    private Long id;
    private String title;
    private String description;
    private String releaseYear;
    private Double rating;
    private Profile director;
    private List<Profile> cast;
    private List<Genre> genres;
    private Time runningTime;
}
