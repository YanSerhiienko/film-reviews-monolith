package com.seyan.reviewmonolith.filmList;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmListRepository extends JpaRepository<FilmList, Long> {
    List<FilmList> findByUserId(Long id);
}
