package com.seyan.reviewmonolith.log;

import com.seyan.reviewmonolith.exception.film.ActivityDeleteException;
import com.seyan.reviewmonolith.exception.film.ActivityNotFoundException;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.log.dto.ActivityOnFilmMapper;
import com.seyan.reviewmonolith.log.dto.ActivityOnFilmRequest;
import com.seyan.reviewmonolith.log.dto.ActivityReviewDiaryRequest;
import com.seyan.reviewmonolith.review.Review;
import com.seyan.reviewmonolith.review.ReviewRepository;
import com.seyan.reviewmonolith.review.ReviewService;
import com.seyan.reviewmonolith.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityOnFilmService {
    private final ActivityOnFilmRepository activityRepository;
    private final ActivityOnFilmMapper activityMapper;
    private final FilmService filmService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @Transactional
    public ActivityOnFilm createOrUpdateActivity(ActivityReviewDiaryRequest request) {
        ActivityOnFilm activity = activityMapper.mapActivityReviewDiaryRequestToActivityOnFilm(request);

        if (request.reviewContent() != null) {
            Review review = reviewService.createReview(request);
            activity.getFilmReviews().add(review);
            activity.setIsInWatchlist(false);
            userService.removeFilmFromWatchlist(request.userId(), request.filmId());
        }

        return activityRepository.save(activity);

        /*if (request.watchedOnDate() != null) {
            DiaryRecord record = activityMapper.mapActivityReviewDiaryRequestToDiaryRecord(request);
        }*/

    }

    public List<ActivityOnFilm> getWatchedFilmsActivities(Long userId) {
        //todo repo method
        return activityRepository.findAll();
    }

    public List<ActivityOnFilm> getLikedFilmsActivities(Long userId) {
        //todo repo method
        return activityRepository.findAll();
    }

    /*public List<ActivityOnFilm> getAllActivities() {
        return activityRepository.findAll();
    }*/

    /*public List<ActivityOnFilm> getAllActivitiesByUserId(Long userId) {
        return activityRepository.findByUserId(userId);
    }*/

    /*public ActivityOnFilm getActivityById(ActivityOnFilmId id) {
        return activityRepository.findById(id).orElseThrow(() -> new ActivityNotFoundException(
                String.format("No film activity found with the provided ID: %s", id)
        ));
    }*/

    public ActivityOnFilm getOrCreateActivityById(ActivityOnFilmId id) {
        return activityRepository.findById(id).orElseGet(() -> ActivityOnFilm.builder().id(id).build());
    }

    //todo check for null exceptions
    @Transactional
    public ActivityOnFilm updateIsLiked(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
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
    public ActivityOnFilm updateRating(Long userId, Long filmId, Double rating) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        activity.setRating(rating);
        activity.setIsWatched(true);
        activityRepository.save(activity);
        Double avgRating = getFilmAvgRating(filmId);
        filmService.updateAvgRating(filmId, avgRating);
        return activity;
    }

    @Transactional
    public ActivityOnFilm removeRating(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        activity.setRating(null);
        activityRepository.save(activity);
        Double avgRating = getFilmAvgRating(filmId);
        filmService.updateAvgRating(filmId, avgRating);
        return activity;
    }

    private Double getFilmAvgRating(Long filmId) {
        return activityRepository.getFilmAvgRating(filmId);
    }


    //todo cant be removed from watched while has reviews or rating
    @Transactional
    public ActivityOnFilm updateIsWatched(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));

        if (activity.getIsWatched()) {

            if (activity.getRating() != null || activity.getFilmReviews().size() > 0) {
                throw new ActivityDeleteException(
                        String.format("Film with the provided ID has rating or reviews and cannot be removed from watched: %s", filmId)
                );
            }

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
    public ActivityOnFilm updateIsInWatchlist(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        if (activity.getIsInWatchlist()) {
            activity.setIsInWatchlist(false);
            userService.removeFilmFromWatchlist(userId, filmId);
        } else {
            activity.setIsInWatchlist(true);
            userService.addFilmToWatchlist(userId, filmId);
        }
        return activityRepository.save(activity);
    }

    //todo deleteIfEmpty (?)
    private boolean deleteIfEmpty(ActivityOnFilm activity) {

    }

    /*public ActivityOnFilm updateHasReview(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        if (activity.getHasReview()) {
            activity.setHasReview(false);
        } else {
            activity.setHasReview(true);
        }
        return activityRepository.save(activity);
    }*/
}
