package com.seyan.reviewmonolith.user;

import com.seyan.reviewmonolith.user.dto.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyGroup;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Role role;

    List<Long> watchedFilms;

    List<Long> watchlistFilms;

    List<Long> likesFilms;

    List<Long> likesReviews;

    List<Long> likesLists;

    List<Long> followingUsers;

    List<Long> followersUsers;

    List<Long> blockedUsers;
}