package com.seyan.reviewmonolith.filmList;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ListEntryId implements Serializable {
    private Long listId;
    private Long filmId;
}
