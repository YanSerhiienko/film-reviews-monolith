package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.profile.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
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

    private LocalDate releaseDate;

    //todo url
    @Column(unique = true)
    private String filmUrl;

    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Profile director;

    //todo many to many
    @ManyToMany
    @JoinTable(
            name = "film_cast",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private List<Profile> cast;
    //private List<Long> castProfiles;

    /*@Enumerated(EnumType.STRING)
    private List<Genre> genres;*/
    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Integer runningTimeMinutes;

    private Long watchedCount;

    private Long listCount;

    private Long likeCount;
}
