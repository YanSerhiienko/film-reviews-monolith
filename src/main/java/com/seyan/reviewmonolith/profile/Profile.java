package com.seyan.reviewmonolith.profile;

import com.seyan.reviewmonolith.film.Film;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
@DynamicUpdate
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String biography;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "cast")
    private List<Film> starringFilms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "director")
    private List<Film> directedFilms;
}
