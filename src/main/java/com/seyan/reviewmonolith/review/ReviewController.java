package com.seyan.reviewmonolith.review;

import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import com.seyan.reviewmonolith.review.dto.ReviewCreationDTO;
import com.seyan.reviewmonolith.review.dto.ReviewMapper;
import com.seyan.reviewmonolith.review.dto.ReviewResponseDTO;
import com.seyan.reviewmonolith.review.dto.ReviewUpdateDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    /*@PostMapping("/create-request")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponseWrapper<ReviewResponseDTO>> createReview(@RequestBody @Valid ActivityReviewDiaryRequest request) {
        Review review = reviewService.createReview(request);
        ReviewResponseDTO response = reviewMapper.mapReviewToReviewResponseDTO(review);
        CustomResponseWrapper<ReviewResponseDTO> wrapper = CustomResponseWrapper.<ReviewResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Review has been successfully created")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }*/

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponseWrapper<ReviewResponseDTO>> createReview(@RequestBody @Valid ReviewCreationDTO dto) {
        Review review = reviewService.createReview(dto);
        ReviewResponseDTO response = reviewMapper.mapReviewToReviewResponseDTO(review);
        CustomResponseWrapper<ReviewResponseDTO> wrapper = CustomResponseWrapper.<ReviewResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Review has been successfully created")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<CustomResponseWrapper<ReviewResponseDTO>> updateReview(@RequestBody @Valid ReviewUpdateDTO dto) {
        Review review = reviewService.updateReview(dto);
        ReviewResponseDTO response = reviewMapper.mapReviewToReviewResponseDTO(review);
        CustomResponseWrapper<ReviewResponseDTO> wrapper = CustomResponseWrapper.<ReviewResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Review has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CustomResponseWrapper<ReviewResponseDTO>> deleteReview(@PathVariable("id") Long reviewId) {
        reviewService.deleteReview(reviewId);
        CustomResponseWrapper<ReviewResponseDTO> wrapper = CustomResponseWrapper.<ReviewResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User has been deleted")
                .data(null)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }


    //todo /username/films/review (?)
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponseWrapper<ReviewResponseDTO>> reviewDetails(@PathVariable("id") Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        ReviewResponseDTO response = reviewMapper.mapReviewToReviewResponseDTO(review);
        CustomResponseWrapper<ReviewResponseDTO> wrapper = CustomResponseWrapper.<ReviewResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Review details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/by-film")
    public ResponseEntity<CustomResponseWrapper<List<ReviewResponseDTO>>> getAllReviewsByFilmId(@RequestParam Long filmId) {
        List<Review> reviews = reviewService.getAllReviewsByFilmId(filmId);
        List<ReviewResponseDTO> response = reviewMapper.mapReviewToReviewResponseDTO(reviews);
        CustomResponseWrapper<List<ReviewResponseDTO>> wrapper = CustomResponseWrapper.<List<ReviewResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message(String.format("List of reviews for film with ID: %s", filmId))
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/by-user")
    public ResponseEntity<CustomResponseWrapper<List<ReviewResponseDTO>>> getAllReviewsByUserId(@RequestParam Long filmId) {
        List<Review> reviews = reviewService.getAllReviewsByUserId(filmId);
        List<ReviewResponseDTO> response = reviewMapper.mapReviewToReviewResponseDTO(reviews);
        CustomResponseWrapper<List<ReviewResponseDTO>> wrapper = CustomResponseWrapper.<List<ReviewResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message(String.format("List of reviews for user with ID: %s", filmId))
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponseWrapper<List<ReviewResponseDTO>>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<ReviewResponseDTO> response = reviewMapper.mapReviewToReviewResponseDTO(reviews);
        CustomResponseWrapper<List<ReviewResponseDTO>> wrapper = CustomResponseWrapper.<List<ReviewResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all reviews")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/count-reviews")
    public ResponseEntity<CustomResponseWrapper<String>> countUserReviewsForFilm(@RequestParam Long userId, @RequestParam Long filmId) {
        int response = reviewService.countUserReviewsForFilm(userId, filmId);
        CustomResponseWrapper<String> wrapper = CustomResponseWrapper.<String>builder()
                .status(HttpStatus.OK.value())
                .message("List of all reviews")
                .data(String.valueOf(response))
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
