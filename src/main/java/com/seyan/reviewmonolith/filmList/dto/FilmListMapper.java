package com.seyan.reviewmonolith.filmList.dto;


import com.seyan.reviewmonolith.film.Film;
import com.seyan.reviewmonolith.filmList.FilmList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.*;

@Component
public class FilmListMapper {
    public FilmList mapFilmListCreationDTOToFilmList(FilmListCreationDTO dto) {
        FilmList filmList = new FilmList();
        BeanUtils.copyProperties(dto, filmList, getNullFieldNames(dto));
        return filmList;
    }

    public FilmList mapFlmListUpdateDTOToFilmList(FilmListUpdateDTO source, FilmList destination) {
        BeanUtils.copyProperties(source, destination, getNullFieldNames(source));
        return destination;
    }

    public FilmListResponseDTO mapFilmListToFilmListResponseDTO(FilmList filmList) {
        //List<FilmInFilmListResponseDTO> filmsResponse = mapFilmToFilmInFilmListResponseDTO(films);
        return new FilmListResponseDTO(
                filmList.getId(),
                filmList.getUserId(),
                filmList.getTitle(),
                filmList.getDescription(),
                filmList.getPrivacy(),
                filmList.getLikeCount(),
                filmList.getCommentCount(),
                Collections.emptyMap()
                //filmsResponse
        );
    }

    public List<FilmListResponseDTO> mapFilmListToFilmListResponseDTO(List<FilmList> filmLists) {
        if (filmLists == null) {
            return null;
        }

        return filmLists.stream()
                .map(this::mapFilmListToFilmListResponseDTO)
                .toList();
    }

    public FilmInFilmListResponseDTO mapFilmToFilmInFilmListResponseDTO(Film film) {
        if (film == null) {
            return null;
        }
        return new FilmInFilmListResponseDTO(film.getId(), film.getTitle(), film.getUrl());
    }

    public List<FilmInFilmListResponseDTO> mapFilmToFilmInFilmListResponseDTO(List<Film> films) {
        if (films == null) {
            return null;
        }
        return films.stream()
                .map(this::mapFilmToFilmInFilmListResponseDTO)
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
