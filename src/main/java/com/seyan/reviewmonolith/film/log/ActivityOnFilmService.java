package com.seyan.reviewmonolith.film.log;

import com.seyan.reviewmonolith.exception.film.ActivityDeleteException;
import com.seyan.reviewmonolith.exception.film.ActivityNotFoundException;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.film.log.dto.ActivityOnFilmMapper;
import com.seyan.reviewmonolith.film.log.dto.ActivityReviewDiaryRequest;
import com.seyan.reviewmonolith.review.Review;
import com.seyan.reviewmonolith.review.ReviewService;
import com.seyan.reviewmonolith.review.dto.ReviewCreationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityOnFilmService {
    private final ActivityOnFilmRepository activityRepository;
    private final ActivityOnFilmMapper activityMapper;
    private final FilmService filmService;

    //todo replace review service with controller methods
    private final ReviewService reviewService;



    //todo add external review / diary entities
    public ActivityOnFilm createOrUpdateActivity(ActivityReviewDiaryRequest request) {
        ActivityOnFilm activity = activityMapper.mapActivityReviewDiaryRequestToActivityOnFilm(request);

        if (request.reviewContent() != null) {
            ReviewCreationDTO dto = activityMapper.mapActivityReviewDiaryRequestToReviewCreationDTO(request);
            Review review = reviewService.createReview(dto);
            //activity.getFilmReviews().add(review);
            activity.setIsInWatchlist(false);
            //userService.removeFilmFromWatchlist(request.userId(), request.filmId());
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

    //////////////////////////////////////////////// DEBUG METHODS
    public List<ActivityOnFilm> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<ActivityOnFilm> getAllActivitiesByUserId(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public ActivityOnFilm getActivityById(ActivityOnFilmId id) {
        return activityRepository.findById(id).orElseThrow(() -> new ActivityNotFoundException(
                String.format("No film activity found with the provided ID: %s", id)
        ));
    }
    ////////////////////////////////////////////////

    public ActivityOnFilm getOrCreateActivityById(ActivityOnFilmId id) {
        return activityRepository.findById(id).orElseGet(() -> ActivityOnFilm.builder()
                .id(id)
                .isWatched(false)
                .isLiked(false)
                .isInWatchlist(false)
                .rating(0.0)
                .build());
    }

    //todo check for null exceptions
    @Transactional
    public ActivityOnFilm updateIsLiked(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        if (activity.getIsLiked()) {
            activity.setIsLiked(false);
            filmService.addLikeCount(filmId, false);
            //userService.removeFilmFromLiked(userId, filmId);
        } else {
            activity.setIsLiked(true);
            filmService.addLikeCount(filmId, true);
            //userService.addFilmToLiked(userId, filmId);
        }
        return activityRepository.save(activity);
    }

    @Transactional
    public ActivityOnFilm updateRating(Long userId, Long filmId, Double rating) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        activity.setRating(rating);
        activity.setIsWatched(true);
        activity.setIsInWatchlist(false);
        activityRepository.save(activity);
        Double avgRating = getFilmAvgRating(filmId);
        filmService.updateAvgRating(filmId, avgRating);
        return activity;
    }

    @Transactional
    public ActivityOnFilm removeRating(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        activity.setRating(0.0);
        activityRepository.save(activity);
        Double avgRating = getFilmAvgRating(filmId);
        filmService.updateAvgRating(filmId, avgRating);
        return activity;
    }

    private Double getFilmAvgRating(Long filmId) {
        return activityRepository.getFilmAvgRating(filmId);
    }


    @Transactional
    public ActivityOnFilm updateIsWatched(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));

        if (activity.getIsWatched()) {
            boolean isHasReviews = checkIfHasReviews(userId, filmId);
            if (activity.getRating() != null || isHasReviews) {
                throw new ActivityDeleteException(
                        String.format("Film with the provided ID has rating or reviews and cannot be removed from watched: %s", filmId)
                );
            }

            activity.setIsWatched(false);

            filmService.addWatchedCount(filmId, false);

            //userService.removeFilmFromWatched(userId, filmId);
        } else {
            activity.setIsWatched(true);
            filmService.addWatchedCount(filmId, true);
            //userService.addFilmToWatched(userId, filmId);
        }

        return activityRepository.save(activity);
    }

    //todo return true if review service is not responding (on microservice layer)
    private boolean checkIfHasReviews(Long userId, Long filmId) {
        int reviewCount = reviewService.countUserReviewsForFilm(userId, filmId);
        return reviewCount > 0;
    }


    //todo list service
    //@Transactional
    public ActivityOnFilm updateIsInWatchlist(Long userId, Long filmId) {
        ActivityOnFilm activity = getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        if (activity.getIsInWatchlist()) {
            activity.setIsInWatchlist(false);
            //userService.removeFilmFromWatchlist(userId, filmId);
        } else {
            activity.setIsInWatchlist(true);
            activity.setWatchlistAddDate(LocalDate.now());
            //userService.addFilmToWatchlist(userId, filmId);
        }
        return activityRepository.save(activity);
    }

    //todo deleteIfEmpty (?) probably i should never use this method
    private void deleteIfEmpty(ActivityOnFilm activity) {
        if (!activity.getIsWatched()
                & activity.getRating() == 0.0
                & !activity.getIsLiked()
                & !activity.getIsInWatchlist()
        ) {
            activityRepository.deleteById(activity.getId());
        }
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
