package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.film.dto.FilmCreationDTO;
import com.seyan.reviewmonolith.film.dto.FilmUpdateDTO;
import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import com.seyan.reviewmonolith.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<CustomResponseWrapper<Film>> filmDetails(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "year", required = false) String year) {
        User user = userService.getUserById(userId);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CustomResponseWrapper<List<UserResponseDTO>>> getAll() {
        List<User> allUsers = userService.getAllUsers();
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(allUsers);
        CustomResponseWrapper<List<UserResponseDTO>> wrapper = CustomResponseWrapper.<List<UserResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all users")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/pageable")
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
    }
}
