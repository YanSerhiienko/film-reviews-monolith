package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.film.dto.FilmMapper;
import com.seyan.reviewmonolith.activity.ActivityOnFilmRepository;
import com.seyan.reviewmonolith.profile.ProfileRepository;
import com.seyan.reviewmonolith.review.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ActivityOnFilmRepository activityRepository;
    @Mock
    private ReviewService reviewService;
    @Spy
    private FilmMapper filmMapper;
    @InjectMocks
    private FilmService filmService;


    @Test
    void createFilm() {
    }

    @Test
    void getFilmById() {
    }

    @Test
    void updateFilm() {
    }

    @Test
    void addCastMember() {
    }

    @Test
    void removeCastMember() {
    }

    @Test
    void updateDirector() {
    }

    @Test
    void addWatchedCount() {
    }

    @Test
    void addLikeCount() {
    }

    @Test
    void updateAvgRating() {
    }

    @Test
    void deleteFilm() {
    }

    @Test
    void getAllFilmsByTitle() {
    }

    @Test
    void getFilmByUrl() {
    }

    @Test
    void getAllFilmsWithParams() {
    }
}