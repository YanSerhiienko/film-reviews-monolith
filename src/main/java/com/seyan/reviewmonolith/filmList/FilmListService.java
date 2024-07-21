package com.seyan.reviewmonolith.filmList;

import com.seyan.reviewmonolith.exception.filmList.FilmListNotFoundException;
import com.seyan.reviewmonolith.exception.user.UserNotFoundException;
import com.seyan.reviewmonolith.film.Film;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.filmList.dto.FilmListCreationDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListMapper;
import com.seyan.reviewmonolith.user.User;
import com.seyan.reviewmonolith.user.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmListService {
    private final FilmListRepository listRepository;
    private final FilmListMapper listMapper;

    //todo replace with controller calls
    private final FilmService filmService;

    public FilmList createList(FilmListCreationDTO dto) {
        FilmList filmList = listMapper.mapFilmListCreationDTOToFilmList(dto);
        return listRepository.save(filmList);
    }

    public FilmList getListById(Long id) {
        return listRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("No list found with the provided ID: %s", id)
        ));
    }

    //todo add list privacy

    //todo add you watched % of list

    public List<FilmList> getAllFilmLists() {
        return listRepository.findAll();
    }

    public FilmList getAllFilmListsByUserId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("No user found with the provided ID: %s", id)
        ));
    }

    public FilmList updateFilmList(UserUpdateDTO dto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("Cannot update user:: No user found with the provided ID: %s", id)
        ));
        User mapped = userMapper.mapUserUpdateDTOToUser(dto, user);
        return userRepository.save(mapped);
    }

    public void deleteFilmList(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("Cannot delete user:: No user found with the provided ID: %s", id)));
        userRepository.deleteById(id);
    }

    public List<Film> getFilmsFromFilmList(List<Long> filmIds) {
        return filmService.getFilmsByIdList(filmIds);
    }
}
