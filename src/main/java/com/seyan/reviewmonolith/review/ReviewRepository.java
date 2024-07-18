package com.seyan.reviewmonolith.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //int countByUserIdAndByFilmId(Long userId, Long filmId);
    int countByUserIdAndFilmId(Long userId, Long filmId);
}
