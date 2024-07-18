package com.seyan.reviewmonolith.film.log;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ActivityOnFilmId implements Serializable {
    private Long userId;
    private Long filmId;
}
