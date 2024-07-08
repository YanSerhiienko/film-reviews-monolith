package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.exception.film.FilmNotFoundException;
import com.seyan.reviewmonolith.exception.film.IncorrectDateRangeException;
import com.seyan.reviewmonolith.exception.user.UserNotFoundException;
import com.seyan.reviewmonolith.film.dto.FilmCreationDTO;
import com.seyan.reviewmonolith.film.dto.FilmMapper;
import com.seyan.reviewmonolith.film.dto.FilmUpdateDTO;
import com.seyan.reviewmonolith.user.User;
import com.seyan.reviewmonolith.user.dto.PageableUserResponseDTO;
import com.seyan.reviewmonolith.user.dto.UserCreationDTO;
import com.seyan.reviewmonolith.user.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    public Film createFilm(FilmCreationDTO dto) {
        Film film = filmMapper.mapFilmCreationDTOToFilm(dto);
        return filmRepository.save(film);
    }

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", id)));
    }

    /*public Film getFilmByTitle(String title) {
        return filmRepository.findByTitle(title).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided title: %s", title)
        ));
    }*/

    public List<Film> getAllFilmsByTitle(String title) {
        return filmRepository.findAllByTitle(title);
    }

    public List<Film> getAllFilmsByGenre(Genre genre) {
        return filmRepository.findByGenre(genre);
    }

    public List<Film> getAllFilmsByReleaseDate(LocalDate rangeFrom, LocalDate rangeTo) {
        if (rangeFrom.isAfter(rangeTo)) {
            throw new IncorrectDateRangeException(String.format(
                    "Cannot find films:: Date range is incorrect: %s - %s", rangeFrom, rangeTo));
        }
        return filmRepository.findByReleaseDateBetween(rangeFrom, rangeTo);
    }

    public Film getFilmByReleaseDate(LocalDate date) {
        return filmRepository.findByReleaseDate(date).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided release date: %s", date)
        ));
    }

    public Film updateFilm(FilmUpdateDTO dto) {
        Film film = filmRepository.findById(dto.getId()).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", dto.getId())));
        Film mapped = filmMapper.mapFilmUpdateDTOToFilm(dto, film);
        return filmRepository.save(film);
    }

    public void deleteFilm(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", id)));
        filmRepository.deleteById(id);
    }


    //PageableUserResponseDTO getAllUsersPageable(int pageNo, int pageSize);
}
