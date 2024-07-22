package com.seyan.reviewmonolith.filmList;

import com.seyan.reviewmonolith.filmList.dto.FilmInFilmListResponseDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListCreationDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListMapper;
import com.seyan.reviewmonolith.filmList.dto.FilmListResponseDTO;
import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class FilmListController {
    private final FilmListService filmListService;
    private final FilmListMapper filmListMapper;

    @PostMapping("/lists/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponseWrapper<FilmListResponseDTO>> createFilmList(@RequestBody @Valid FilmListCreationDTO dto) {

        FilmList list = filmListService.createList(dto);
        List<FilmInFilmListResponseDTO> films = filmListService.getFilmsFromList(dto.filmIds());

        FilmListResponseDTO response = filmListMapper.mapFilmListToFilmListResponseDTO(list);
        response.setFilms(films);

        CustomResponseWrapper<FilmListResponseDTO> wrapper = CustomResponseWrapper.<FilmListResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("List has been successfully created")
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.CREATED);
    }

    //todo /username/list/listname/edit/

    /*@PatchMapping("/lists/{id}/update")
    public ResponseEntity<CustomResponseWrapper<FilmListResponseDTO>> updateFilmList(@RequestBody @Valid UserUpdateDTO dto, @PathVariable("id") long id) {
        User user = userService.updateUser(dto, id);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User has been updated")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    /*@DeleteMapping("/lists/{id}/delete")
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> deleteFilmList(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User has been deleted")
                .data(null)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    /*@GetMapping("/{userId}/list/{listTitle}")
    public ResponseEntity<CustomResponseWrapper<UserResponseDTO>> filmListDetails(
            @RequestParam(required = false) Map<String, String> params,
            @RequestParam(required = false) Long userId) {

        User user = userService.getUserById(userId);
        UserResponseDTO response = userMapper.mapUserToUserResponseDTO(user);
        CustomResponseWrapper<UserResponseDTO> wrapper = CustomResponseWrapper.<UserResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("User details")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    //todo username/likes/lists/
    //todo /username/lists/

    /*@GetMapping("/lists")
    public ResponseEntity<CustomResponseWrapper<List<UserResponseDTO>>> getAll() {
        List<User> allUsers = userService.getAllUsers();
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(allUsers);
        CustomResponseWrapper<List<UserResponseDTO>> wrapper = CustomResponseWrapper.<List<UserResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all users")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    /*@GetMapping("/{userId}/lists")
    public ResponseEntity<CustomResponseWrapper<List<UserResponseDTO>>> getAllByUserId() {
        List<User> allUsers = userService.getAllUsers();
        List<UserResponseDTO> response = userMapper.mapUserToUserResponseDTO(allUsers);
        CustomResponseWrapper<List<UserResponseDTO>> wrapper = CustomResponseWrapper.<List<UserResponseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("List of all users")
                .data(response)
                .build();
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }*/

    @GetMapping("/lists/{listId}")
    public ResponseEntity<CustomResponseWrapper<FilmListResponseDTO>> getListById(@PathVariable("listId") Long listId) {

        FilmList list = filmListService.getListById(listId);
        List<Long> filmIds = filmListMapper.mapListEntriesToFilmIds(list.filmEntries);
        List<FilmInFilmListResponseDTO> films = filmListService.getFilmsFromList(filmIds);

        FilmListResponseDTO response = filmListMapper.mapFilmListToFilmListResponseDTO(list);
        response.setFilms(films);

        CustomResponseWrapper<FilmListResponseDTO> wrapper = CustomResponseWrapper.<FilmListResponseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message(String.format("List details by ID: %s", listId))
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }
}
