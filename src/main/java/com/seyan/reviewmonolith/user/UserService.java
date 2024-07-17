package com.seyan.reviewmonolith.user;

import com.seyan.reviewmonolith.exception.user.UserNotFoundException;
import com.seyan.reviewmonolith.film.FilmRepository;
import com.seyan.reviewmonolith.user.dto.PageableUserResponseDTO;
import com.seyan.reviewmonolith.user.dto.UserCreationDTO;
import com.seyan.reviewmonolith.user.dto.UserMapper;
import com.seyan.reviewmonolith.user.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FilmRepository filmRepository;
    private final UserMapper userMapper;

    public User createUser(UserCreationDTO dto) {
        User user = userMapper.mapUserCreationDTOToUser(dto);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("No user found with the provided ID: %s", id)
        ));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
                String.format("No user found with the provided username: %s", username)
        ));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(UserUpdateDTO dto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("Cannot update user:: No user found with the provided ID: %s", id)
        ));
        User mapped = userMapper.mapUserUpdateDTOToUser(dto, user);
        return userRepository.save(mapped);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("Cannot delete user:: No user found with the provided ID: %s", id)));
        userRepository.deleteById(id);
    }

    public PageableUserResponseDTO getAllUsersPageable(int pageNo, int pageSize) {
        return null;
    }

    //TODO ignore/remove null from returned list
    /*public List<Film> getWatchedFilms(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
                String.format("No user found with the provided username: %s", username)
        ));
        return filmRepository.findAllById(user.getWatchedFilms());
    }

    public List<Film> getWatchlist(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(
                String.format("No user found with the provided username: %s", username)
        ));
        return filmRepository.findAllById(user.getWatchedFilms());
    }*/

    //todo custom repo select from user
    public List<Long> getLikedFilms(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("No user found with the provided ID: %s", id)
        ));
        return user.getLikedFilms();
    }

    public void addFilmToLiked(Long userId, Long filmId) {
        //todo updateLikedFilms
    }

    public void removeFilmFromLiked(Long userId, Long filmId) {

    }

    public void addFilmToWatched(Long userId, Long filmId) {

    }

    public void removeFilmFromWatched(Long userId, Long filmId) {

    }

    public void addFilmToWatchlist(Long userId, Long filmId) {

    }

    public void removeFilmFromWatchlist(Long userId, Long filmId) {

    }

    public boolean isFilmInWatchList(Long aLong, Long aLong1) {
        return false;
    }


    //PageableUserResponseDTO getAllUsersPageable(int pageNo, int pageSize);
    /*
    api/username/films/diary
    api/username/films/reviews
    api/username/films/reviews/title

    api/username/watchlist
    api/username/lists

    api/username/likes/films
    api/username/likes/reviews
    api/username/likes/lists

    api/username/following
    api/username/followers
    api/username/blocked*/
}
