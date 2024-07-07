package com.seyan.reviewmonolith.film;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    //Film Name
    //List<Film> findAllByOrderByTitleAsc();
    //todo debug search with similar titles
    List<Film> findByOrderByTitleAsc();

    //Release Date
    List<Film> findByOrderByReleaseDateDesc(); //Newest First
    List<Film> findByOrderByReleaseDateAsc(); //Earliest First

    //Average Rating
    List<Film> findByOrderByRatingDesc(); //Highest First
    List<Film> findByOrderByRatingAsc(); //Lowest First

    //Your Rating
    //findByInventoryIdIn(List<Long> inventoryIdList);

    //List<Film> findByOrderByIdAndRatingDescIn(List<Long> filmIds); //Highest First
    List<Film> findByIdByOrderByRatingDescIn(List<Long> filmIds); //Highest First
    List<Film> findByOrderByIdAndRatingAscIn(List<Long> filmIds); //Lowest First

//    Your Interests
//    Based on films you liked
//    Related to films you liked
//    Film Length
//    Shortest First
//    Longest First
//    Film Popularity
//    All Time
//    This Week
//    This Month
//    This Year
//    Film Popularity with Friends
//    All Time
//    This Week
//    This Month
//    This Year
    //Optional<Film> findByTitle(String title);

    List<Film> findByGenre(Genre genre);

    List<Film> findByReleaseDateBetween(LocalDate rangeFrom, LocalDate rangeTo);

    Optional<Film> findByReleaseDate(LocalDate release);

    List<Film> findByRatingDesc(Double rating);

    List<Film> findByRatingAsc(Double rating);

    //findAllByOrderByIdAsc

    //findByBooOrderById
}
