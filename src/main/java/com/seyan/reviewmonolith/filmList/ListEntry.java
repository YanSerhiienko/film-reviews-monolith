package com.seyan.reviewmonolith.filmList;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "list_entry")
public class ListEntry {
    //todo list entry composite key ?
    @EmbeddedId
    private ListEntryId id;
    private LocalDate whenAdded;
}
