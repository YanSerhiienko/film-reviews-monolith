package com.seyan.reviewmonolith.filmList;

import com.seyan.reviewmonolith.filmList.dto.*;
import com.seyan.reviewmonolith.filmList.entry.ListEntry;
import com.seyan.reviewmonolith.filmList.entry.ListEntryRepository;
import com.seyan.reviewmonolith.responseWrapper.CustomResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class FilmListController {
    private final FilmListService filmListService;
    private final FilmListMapper filmListMapper;
    private final ListEntryRepository entryRepository;

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

    @PatchMapping("/lists/{id}/update")
    public ResponseEntity<CustomResponseWrapper<FilmListResponseDTO>> updateFilmList(@RequestBody @Valid FilmListUpdateDTO dto, @PathVariable("id") Long id) {
        FilmList list = filmListService.updateFilmList(dto, id);
        //List<Long> filmIds = list.getFilmIds().stream().map(it -> it.getFilmId()).toList();
        List<Long> filmIds = entryRepository.findByListId(list.getId()).stream().sorted(Comparator.comparing(ListEntry::getEntryOrder)).map(ListEntry::getFilmId).toList();
        List<FilmInFilmListResponseDTO> films = filmListService.getFilmsFromList(filmIds);

        FilmListResponseDTO response = filmListMapper.mapFilmListToFilmListResponseDTO(list);
        response.setFilms(films);

        CustomResponseWrapper<FilmListResponseDTO> wrapper = CustomResponseWrapper.<FilmListResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("List has been successfully updated")
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    @GetMapping("/lists/{listId}")
    public ResponseEntity<CustomResponseWrapper<FilmListResponseDTO>> getListById(@PathVariable("listId") Long listId) {

        FilmList list = filmListService.getListById(listId);
        System.out.println("list = " + list);
        /*List<ListEntry> entries = list.getFilmIds().stream().toList();
        List<Long> filmIds = filmListMapper.mapListEntriesToFilmIds(entries);*/
        List<Long> filmIds = entryRepository.findByListId(list.getId()).stream().sorted(Comparator.comparing(ListEntry::getEntryOrder)).map(ListEntry::getFilmId).toList();
        System.out.println("filmIds = " + filmIds);
        List<FilmInFilmListResponseDTO> films = filmListService.getFilmsFromList(filmIds);

        FilmListResponseDTO response = filmListMapper.mapFilmListToFilmListResponseDTO(list);
        response.setFilms(films);

        CustomResponseWrapper<FilmListResponseDTO> wrapper = CustomResponseWrapper.<FilmListResponseDTO>builder()
                .status(HttpStatus.OK.value())
                .message(String.format("List details by ID: %s", listId))
                .data(response)
                .build();

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

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


}
