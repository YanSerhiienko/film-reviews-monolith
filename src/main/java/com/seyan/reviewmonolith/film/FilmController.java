package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.film.dto.FilmCreationDTO;
import com.seyan.reviewmonolith.film.dto.FilmMapper;
import com.seyan.reviewmonolith.film.dto.FilmResponseDTO;
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
    private final FilmMapper filmMapper;

    @PostMapping("/create")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> createFilm(@RequestBody @Valid FilmCreationDTO dto) {
        Film film = filmService.createFilm(dto);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Film has been successfully created")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> updateFilm(@PathVariable("id") Long id, @RequestBody @Valid FilmUpdateDTO dto) {
        Film film = filmService.updateFilm(dto, id);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> deleteFilm(@PathVariable("id") Long filmId) {
        filmService.deleteFilm(filmId);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film has been deleted")
                .data(null)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> filmDetailsById(@PathVariable(value = "id") Long id) {
        Film film = filmService.getFilmById(id);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/{filmUrl}")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> filmDetailsByUrl(@PathVariable(value = "filmUrl") String filmUrl) {
        Film film = filmService.getFilmByUrl(filmUrl);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CustomResponseWrapper<List<FilmResponseDTO>>> getAllFilms(
            @RequestParam(required = false) Map<String, String> params, @RequestParam(required = false) Long userId) {
        List<Film> films = filmService.getAllFilmsWithParams(params, userId);
        List<FilmResponseDTO> response = filmMapper.mapFilmToFilmResponseDTO(films);
        CustomResponseWrapper<List<FilmResponseDTO>> wrapper = CustomResponseWrapper.<List<FilmResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all films")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<CustomResponseWrapper<List<FilmResponseDTO>>> getAllFilmsByTitle(@PathVariable String title) {
        //todo parse title with blank spaces(?)
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!title = " + title);
        List<Film> films = filmService.getAllFilmsByTitle(title);
        List<FilmResponseDTO> response = filmMapper.mapFilmToFilmResponseDTO(films);
        CustomResponseWrapper<List<FilmResponseDTO>> wrapper = CustomResponseWrapper.<List<FilmResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all films by title")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("{id}/update/add-cast")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> addFilmCastMember(
            @PathVariable(value = "id") Long filmId, @RequestBody List<Long> profileIdList) {

        Film film = filmService.addCastMember(profileIdList, filmId);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);

        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film with updated cast")
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("{id}/update/remove-cast")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> removeFilmCastMember
            (@PathVariable(value = "id") Long filmId, @RequestBody List<Long> profileIdList) {

        Film film = filmService.removeCastMember(profileIdList, filmId);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);

        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film with updated cast")
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @PatchMapping("{id}/update/director")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> updateFilmDirector
            (@PathVariable(value = "id") Long filmId, @RequestParam(required = false) Long directorId) {

        Film film = filmService.updateDirector(directorId, filmId);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);

        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film with updated cast")
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /*@PatchMapping("/update")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> updateFilm(@RequestBody @Valid FilmUpdateDTO dto) {
        Film film = filmService.updateFilm(dto);
        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

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

    /*@PatchMapping("{id}/update/add-cast")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> addFilmCastMember(
            @PathVariable(value = "id") Long filmId, @RequestParam(required = false) Long profileId,
            @RequestBody(required = false) List<Long> profileIdList) {

        Film film = new Film();

        if ((profileId == null & profileIdList == null)
        || (profileId != null & profileIdList != null)) {
            CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Request should contain profile id OR profile id list")
                    .data(null)
                    .build();
            return new ResponseEntity<>(wrapper, HttpStatus.BAD_REQUEST);
        } else if (profileId != null) {
            film = filmService.addCastMember(profileId, filmId);
        } else {
            film = filmService.addCastMember(profileIdList, filmId);
        }

        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film with updated cast")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    /*@PatchMapping("{id}/update/remove-cast")
    public ResponseEntity<CustomResponseWrapper<FilmResponseDTO>> removeFilmCastMember
            (@PathVariable(value = "id") Long filmId, @RequestParam(required = false) Long profileId, @RequestBody(required = false) List<Long> profileIdList) {

        Film film = new Film();

        if ((profileId == null & profileIdList == null)
                || (profileId != null & profileIdList != null)) {
            CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Request should contain profile id OR profile id list")
                    .data(null)
                    .build();
            return new ResponseEntity<>(wrapper, HttpStatus.BAD_REQUEST);
        } else if (profileId != null) {
            film = filmService.removeCastMember(profileId, filmId);
        } else {
            film = filmService.removeCastMember(profileIdList, filmId);
        }

        FilmResponseDTO response = filmMapper.mapFilmToFilmResponseDTO(film);
        CustomResponseWrapper<FilmResponseDTO> wrapper = CustomResponseWrapper.<FilmResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Film with updated cast")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/
}
