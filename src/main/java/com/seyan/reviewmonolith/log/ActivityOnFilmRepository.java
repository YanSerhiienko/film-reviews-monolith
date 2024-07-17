package com.seyan.reviewmonolith.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityOnFilmRepository extends JpaRepository<ActivityOnFilm, ActivityOnFilmId> {
    @Query(value = "select * from user_film_activity where id.user_id = :userId", nativeQuery = true)
    List<ActivityOnFilm> findByUserId(@Param("userId") Long userId);

    @Query(value = "select avg(rating) from user_film_activity where id.film_id = :filmId", nativeQuery = true)
    Double getFilmAvgRating(@Param("filmId") Long filmId);
}
