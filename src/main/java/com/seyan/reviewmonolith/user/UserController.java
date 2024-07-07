package com.seyan.reviewmonolith.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class UserController {
    public ResponseEntity<User> createUser() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*api/username
    api/username/films(watched)
    api/username/films/diary
    api/username/films/reviews

    api/username/watchlist
    api/username/lists

    api/username/likes/films
    api/username/likes/reviews
    api/username/likes/lists

    api/username/following
    api/username/followers
    api/username/blocked*/
}
