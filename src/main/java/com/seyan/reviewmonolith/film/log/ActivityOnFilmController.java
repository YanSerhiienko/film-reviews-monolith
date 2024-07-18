package com.seyan.reviewmonolith.film.log;

import com.seyan.reviewmonolith.film.log.dto.ActivityOnFilmMapper;
import com.seyan.reviewmonolith.film.log.dto.ActivityOnFilmResponse;
import com.seyan.reviewmonolith.film.log.dto.ActivityReviewDiaryRequest;
import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import com.seyan.reviewmonolith.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class ActivityOnFilmController {
    private final ActivityOnFilmService activityService;
    private final ActivityOnFilmMapper activityMapper;
    private final UserService userService;

    /*@PostMapping("/create")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> createOrUpdateActivity(@RequestBody @Valid ActivityOnFilmRequest dto) {
        ActivityOnFilm activity = activityService.createOrUpdateActivity(dto);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Film activity has been successfully created or updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    @PostMapping("/activity/create-update")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> createOrUpdateActivity(@RequestBody @Valid ActivityReviewDiaryRequest request) {
        ActivityOnFilm activity = activityService.createOrUpdateActivity(request);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Film activity has been successfully created or updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}/films/")
    public ResponseEntity<CustomResponseWrapper<List<ActivityOnFilmResponse>>> getWatchedFilmsActivities(@PathVariable("id") Long userId) {
        List<ActivityOnFilm> activityList = activityService.getWatchedFilmsActivities(userId);
        List<ActivityOnFilmResponse> response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activityList);
        CustomResponseWrapper<List<ActivityOnFilmResponse>> wrapper = CustomResponseWrapper.<List<ActivityOnFilmResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(String.format("Watched films of user with ID: %s", userId))
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}/films/liked")
    public ResponseEntity<CustomResponseWrapper<List<ActivityOnFilmResponse>>> getLikedFilmsActivities(@PathVariable("id") Long userId) {
        List<ActivityOnFilm> activityList = activityService.getLikedFilmsActivities(userId);
        List<ActivityOnFilmResponse> response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activityList);
        CustomResponseWrapper<List<ActivityOnFilmResponse>> wrapper = CustomResponseWrapper.<List<ActivityOnFilmResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(String.format("Liked films of user with ID: %s", userId))
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/activity/create-update")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> getFilmActivity(@RequestParam("userId") Long userId, @RequestParam("filmId") Long filmId) {
        ActivityOnFilm activity = activityService.getOrCreateActivityById(new ActivityOnFilmId(userId, filmId));
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message(String.format("User with ID %s activity for film with ID: %s", userId, filmId))
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("/activity/update-is-liked")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> updateIsLiked(
            @RequestParam("userId") Long userId, @RequestParam("filmId") Long filmId) {
        ActivityOnFilm activity = activityService.updateIsLiked(userId, filmId);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Like status has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("/activity/update-rating")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> updateRating(
            @RequestParam("userId") Long userId, @RequestParam("filmId") Long filmId, @RequestBody Double rating) {
        ActivityOnFilm activity = activityService.updateRating(userId, filmId, rating);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Rating has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("/activity/remove-rating")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> removeRating(
            @RequestParam("userId") Long userId, @RequestParam("filmId") Long filmId) {

        ActivityOnFilm activity = activityService.removeRating(userId, filmId);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Rating has been removed")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("/activity/update-is-watched")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> updateIsWatched(
            @RequestParam("userId") Long userId, @RequestParam("filmId") Long filmId) {

        ActivityOnFilm activity = activityService.updateIsWatched(userId, filmId);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Is watched status has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("/activity/update-is-in-watchlist")
    public ResponseEntity<CustomResponseWrapper<ActivityOnFilmResponse>> updateIsInWatchlist(
            @RequestParam("userId") Long userId, @RequestParam("filmId") Long filmId) {

        ActivityOnFilm activity = activityService.updateIsInWatchlist(userId, filmId);
        ActivityOnFilmResponse response = activityMapper.mapActivityOnFilmToActivityOnFilmResponse(activity);
        CustomResponseWrapper<ActivityOnFilmResponse> wrapper = CustomResponseWrapper.<ActivityOnFilmResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Is in watchlist status has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
