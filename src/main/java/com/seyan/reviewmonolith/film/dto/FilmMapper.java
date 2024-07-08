package com.seyan.reviewmonolith.film.dto;


import com.seyan.reviewmonolith.film.Film;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Component
public class FilmMapper {
    public Film mapFilmCreationDTOToFilm(FilmCreationDTO dto) {
        return Film.builder()
                .title(dto.title())
                .description(dto.description())
                .releaseDate(dto.releaseDate())
                .director(dto.director())
                .castProfiles(dto.castProfiles())
                .genres(dto.genres())
                .runningTime(dto.runningTime())
                .build();
    }

    public Film mapFilmUpdateDTOToFilm(FilmUpdateDTO source, Film destination) {
        BeanUtils.copyProperties(source, destination, getNullFieldNames(source));
        return destination;
    }

    /*public UserProfileResponseDTO mapUserToUserProfileResponseDTO(User user) {
        UserProfileResponseDTO response = new UserProfileResponseDTO();
        BeanUtils.copyProperties(user, response);
        return response;
    }*/

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
}
