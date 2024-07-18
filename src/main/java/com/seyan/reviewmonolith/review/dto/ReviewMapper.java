package com.seyan.reviewmonolith.review.dto;


import com.seyan.reviewmonolith.film.log.dto.ActivityReviewDiaryRequest;
import com.seyan.reviewmonolith.review.Review;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ReviewMapper {
    public Review mapReviewCreationDTOToReview(ReviewCreationDTO dto) {
        Review review = new Review();
        BeanUtils.copyProperties(dto, review, getNullFieldNames(dto));
        return review;
        /*return Review.builder()
                //.rating(dto.rating())
                //.isLikedFilm(dto.isLikedFilm())
                .content(dto.content())
                .watchedOnDate(dto.watchedOnDate())
                .filmId(dto.filmId())
                .authorId(dto.authorId())
                .containsSpoilers(dto.containsSpoilers())
                .watchedThisFilmBefore(dto.watchedThisFilmBefore())
                .build();*/
    }

    public Review mapReviewUpdateDTOToReview(ReviewUpdateDTO source, Review destination) {
        BeanUtils.copyProperties(source, destination, getNullFieldNames(source));
        return destination;
    }

    //todo copy properties
    public ReviewResponseDTO mapReviewToReviewResponseDTO(Review review) {
        //BeanUtils.copyProperties(profile, response);
        return new ReviewResponseDTO(
                review.getId(),
                review.getRating(),
                review.getIsLiked(),
                review.getContent(),
                review.getContainsSpoilers(),
                review.getCreationDate(),
                review.getFilmId(),
                review.getAuthorId(),
                review.getLikedUsersIds(),
                review.getCommentIds()
        );
    }

    /*public PageableUserResponseDTO mapUsersPageToPageableUserResponseDTO(Page<User> usersPage) {
        List<User> listOfUsers = usersPage.getContent();
        List<UserProfileResponseDTO> userProfileResponseDTO = mapUserToUserProfileResponseDTO(listOfUsers);

        return PageableUserResponseDTO.builder()
                .content(userProfileResponseDTO)
                .pageNo(usersPage.getNumber())
                .pageSize(usersPage.getSize())
                .totalElements(usersPage.getTotalElements())
                .totalPages(usersPage.getTotalPages())
                .last(usersPage.isLast()).build();
    }*/

    public List<ReviewResponseDTO> mapReviewToReviewResponseDTO(List<Review> reviews) {
        if (reviews == null) {
            return null;
        }

        return reviews.stream()
                .map(this::mapReviewToReviewResponseDTO)
                .toList();
    }

    private String[] getNullFieldNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> fieldNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
                fieldNames.add(pd.getName());
        }

        String[] result = new String[fieldNames.size()];
        return fieldNames.toArray(result);
    }
}
