package com.seyan.reviewmonolith.film.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityOnFilmRepository extends JpaRepository<ActivityOnFilm, ActivityOnFilmId> {
    @Query(value = "select * from activity_on_film where id.user_id = :userId", nativeQuery = true)
    List<ActivityOnFilm> findByUserId(@Param("userId") Long userId);

    @Query(value = "select avg(rating) from activity_on_film where id.film_id = :filmId and rating > 0.0", nativeQuery = true)
    Double getFilmAvgRating(@Param("filmId") Long filmId);

    @Query(value = "select * from activity_on_film where id.user_id = :userId and is_watched = true", nativeQuery = true)
    List<ActivityOnFilm> findWatchedFilmsActivities(@Param("userId") Long userId);

    @Query(value = "select * from activity_on_film where id.user_id = :userId and is_liked = true", nativeQuery = true)
    List<ActivityOnFilm> findLikedFilmsActivities(@Param("userId") Long userId);

    List<Long> findIdFilmIdByIdUserIdAndByRatingGreaterThanEqual(Long userId, Double rating);

    @Query(value = "select id.film_id from activity_on_film where id.user_id = :userId and rating > 0.0", nativeQuery = true)
    List<ActivityOnFilm> findFilmIdByUserIdAndByRatingGreaterThan(@Param("userId") Long userId, @Param("rating") Double rating);

    //countByJobIdAndToDeleteIsTrue
}
