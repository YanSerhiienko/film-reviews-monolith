package com.seyan.reviewmonolith.review.filmActivity;

import com.seyan.reviewmonolith.exception.film.ActivityNotFoundException;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.review.filmActivity.dto.UserActivityOnFilmMapper;
import com.seyan.reviewmonolith.review.filmActivity.dto.UserActivityOnFilmRequest;
import com.seyan.reviewmonolith.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserActivityOnFilmService {
    private final UserActivityOnFilmRepository activityRepository;
    private final UserActivityOnFilmMapper activityMapper;
    private final FilmService filmService;
    private final UserService userService;

    public UserActivityOnFilm createOrUpdateActivity(UserActivityOnFilmRequest request) {
        UserActivityOnFilm activity = activityMapper.mapUserActivityOnFilmRequestToUserActivityOnFilm(request);
        return activityRepository.save(activity);
    }

    public List<UserActivityOnFilm> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<UserActivityOnFilm> getAllActivitiesByUserId(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public UserActivityOnFilm getActivityById(UserActivityOnFilmId id) {
        return activityRepository.findById(id).orElseThrow(() -> new ActivityNotFoundException(
                String.format("No film activity found with the provided ID: %s", id)
        ));
    }

    private UserActivityOnFilm getOrCreateActivityById(UserActivityOnFilmId id) {
        return activityRepository.findById(id).orElseGet(() -> UserActivityOnFilm.builder().id(id).build());
    }

    //todo check for null exceptions
    @Transactional
    public UserActivityOnFilm updateIsLikedFilm(Long userId, Long filmId) {
        UserActivityOnFilm activity = getOrCreateActivityById(new UserActivityOnFilmId(userId, filmId));
        if (activity.getIsLiked()) {
            activity.setIsLiked(false);
            filmService.updateLikeCount(false);
            userService.removeFilmFromLiked(userId, filmId);
        } else {
            activity.setIsLiked(true);
            filmService.updateLikeCount(true);
            userService.addFilmToLiked(userId, filmId);
        }
        return activityRepository.save(activity);
    }

    @Transactional
    public UserActivityOnFilm updateRating(Long userId, Long filmId, Double rating) {
        UserActivityOnFilm activity = getOrCreateActivityById(new UserActivityOnFilmId(userId, filmId));
        activity.setRating(rating);
        activity.setIsWatched(true);
        activityRepository.save(activity);
        Double avgRating = getFilmAvgRating(filmId);
        filmService.updateAvgRating(filmId, avgRating);
        return activity;
    }

    @Transactional
    public UserActivityOnFilm removeRating(Long userId, Long filmId) {
        UserActivityOnFilm activity = getOrCreateActivityById(new UserActivityOnFilmId(userId, filmId));
        activity.setRating(null);
        activityRepository.save(activity);
        Double avgRating = getFilmAvgRating(filmId);
        filmService.updateAvgRating(filmId, avgRating);
        return activity;
    }

    private Double getFilmAvgRating(Long filmId) {
        return activityRepository.getFilmAvgRating(filmId);
    }

    @Transactional
    public UserActivityOnFilm updateIsWatched(Long userId, Long filmId) {
        UserActivityOnFilm activity = getOrCreateActivityById(new UserActivityOnFilmId(userId, filmId));
        if (activity.getIsWatched()) {
            activity.setIsWatched(false);
            filmService.updateWatchedCount(false);
            userService.removeFilmFromWatched(userId, filmId);
        } else {
            activity.setIsWatched(true);
            filmService.updateWatchedCount(true);
            userService.addFilmToWatched(userId, filmId);
        }
        return activityRepository.save(activity);
    }

    @Transactional
    public UserActivityOnFilm updateIsInWatchlist(Long userId, Long filmId) {
        UserActivityOnFilm activity = getOrCreateActivityById(new UserActivityOnFilmId(userId, filmId));
        if (activity.getIsInWatchlist()) {
            activity.setIsInWatchlist(false);
            userService.removeFilmFromWatchlist(userId, filmId);
        } else {
            activity.setIsInWatchlist(true);
            userService.addFilmToWatchlist(userId, filmId);
        }
        return activityRepository.save(activity);
    }

    public UserActivityOnFilm updateHasReview(Long userId, Long filmId) {
        UserActivityOnFilm activity = getOrCreateActivityById(new UserActivityOnFilmId(userId, filmId));
        if (activity.getHasReview()) {
            activity.setHasReview(false);
        } else {
            activity.setHasReview(true);
        }
        return activityRepository.save(activity);
    }
}
