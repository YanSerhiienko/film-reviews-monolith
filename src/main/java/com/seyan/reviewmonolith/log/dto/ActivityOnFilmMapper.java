package com.seyan.reviewmonolith.log.dto;


import com.seyan.reviewmonolith.log.ActivityOnFilm;
import com.seyan.reviewmonolith.log.ActivityOnFilmId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ActivityOnFilmMapper {
    public ActivityOnFilm mapActivityOnFilmRequestToActivityOnFilm(ActivityOnFilmRequest request) {
        ActivityOnFilm activity = new ActivityOnFilm();
        BeanUtils.copyProperties(request, activity, getNullFieldNames(request));
        return activity;
    }

    public ActivityOnFilmResponse mapActivityOnFilmToActivityOnFilmResponse(ActivityOnFilm activity) {
        boolean hasReview = activity.getFilmReviews().size() > 0;
        return new ActivityOnFilmResponse(
                activity.getId(),
                activity.getRating(),
                activity.getIsWatched(),
                activity.getIsLiked(),
                activity.getIsInWatchlist(),
                hasReview
        );
    }

    public List<ActivityOnFilmResponse> mapActivityOnFilmToActivityOnFilmResponse(List<ActivityOnFilm> films) {
        if (films == null) {
            return null;
        }

        return films.stream()
                .map(this::mapActivityOnFilmToActivityOnFilmResponse)
                .toList();
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

    /*public List<UserProfileResponseDTO> mapUserToUserProfileResponseDTO(List<User> users) {
        if (users == null) {
            return null;
        }

        List<UserProfileResponseDTO> list = users.stream()
                .map(this::mapUserToUserProfileResponseDTO)
                .toList();
        return list;
    }*/

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

    public ActivityOnFilm mapActivityReviewDiaryRequestToActivityOnFilm(ActivityReviewDiaryRequest request) {
        return ActivityOnFilm.builder()
                .id(new ActivityOnFilmId(
                        request.userId(),
                        request.filmId()
                ))
                .isWatched(true)
                .isLiked(request.isLiked())
                .rating(request.rating())
                .build();
    }


}
