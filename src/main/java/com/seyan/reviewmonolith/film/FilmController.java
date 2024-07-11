package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.film.dto.FilmCreationDTO;
import com.seyan.reviewmonolith.film.dto.FilmUpdateDTO;
import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api/v1/films")
@RestController
public class FilmController {
    private final FilmService filmService;

    @PostMapping("/create")
    public ResponseEntity<CustomResponseWrapper<Film>> createFilm(@RequestBody @Valid FilmCreationDTO dto) {
        Film film = filmService.createFilm(dto);
        CustomResponseWrapper<Film> wrapper = CustomResponseWrapper.<Film>builder()
                .status(HttpStatus.CREATED.value())
                .message("Film has been successfully created")
                .data(film)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    public ResponseEntity<CustomResponseWrapper<Film>> updateFilm(@RequestBody @Valid FilmUpdateDTO dto) {
        Film film = filmService.updateFilm(dto);
        CustomResponseWrapper<Film> wrapper = CustomResponseWrapper.<Film>builder()
                .status(HttpStatus.OK.value())
                .message("Film has been updated")
                .data(film)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CustomResponseWrapper<Film>> deleteFilm(@PathVariable("id") Long filmId) {
        filmService.deleteFilm(filmId);
        CustomResponseWrapper<Film> wrapper = CustomResponseWrapper.<Film>builder()
                .status(HttpStatus.OK.value())
                .message("Film has been deleted")
                .data(null)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{filmUrl}")
    public ResponseEntity<CustomResponseWrapper<Film>> filmDetails(@PathVariable(value = "filmUrl") String filmUrl) {
        Film film = filmService.getFilmByUrl(filmUrl);
        CustomResponseWrapper<Film> wrapper = CustomResponseWrapper.<Film>builder()
                .status(HttpStatus.OK.value())
                .message("Film details")
                .data(film)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponseWrapper<List<Film>>> getAllFilms(@RequestParam(required = false) Map<String, String> params, @RequestBody(required = false) Long userId) {
        List<Film> films = filmService.getAllFilmsWithParams(params, userId);
        CustomResponseWrapper<List<Film>> wrapper = CustomResponseWrapper.<List<Film>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all films")
                .data(films)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<CustomResponseWrapper<List<Film>>> getAllFilmsByTitle(@PathVariable String title) {
        //todo parse title with blank spaces
        System.out.println("title = " + title);
        List<Film> films = filmService.getAllFilmsByTitle(title);
        CustomResponseWrapper<List<Film>> wrapper = CustomResponseWrapper.<List<Film>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all films by title")
                .data(films)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /*@GetMapping("/pageable")
    public ResponseEntity<CustomResponseWrapper<PageableUserResponseDTO>> getPageable(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        PageableUserResponseDTO allUsersPageable = userService.getAllUsersPageable(pageNo, pageSize);

        CustomResponseWrapper<PageableUserResponseDTO> wrapper = CustomResponseWrapper.<PageableUserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("List of all users")
                .data(allUsersPageable)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/
}
