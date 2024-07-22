package com.seyan.reviewmonolith.filmList;

import com.seyan.reviewmonolith.exception.film.SortingParametersException;
import com.seyan.reviewmonolith.exception.filmList.FilmListNotFoundException;
import com.seyan.reviewmonolith.film.Film;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.filmList.dto.FilmInFilmListResponseDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListCreationDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListMapper;
import com.seyan.reviewmonolith.filmList.dto.FilmListUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FilmListService {
    private final FilmListRepository filmListRepository;
    private final FilmListMapper filmListMapper;

    //todo replace with controller calls
    private final FilmService filmService;

    @Transactional
    public FilmList createList(FilmListCreationDTO dto) {
        LocalDate creationDate = LocalDate.now();

        FilmList mapped = filmListMapper.mapFilmListCreationDTOToFilmList(dto);
        FilmList saved = filmListRepository.save(mapped);

        List<ListEntry> listEntries = filmListMapper.mapFilmIdsToListEntries(saved.getId(), dto.filmIds(), creationDate);

        saved.setFilmEntries(listEntries);
        saved.setCreationDate(creationDate);
        saved.setLastUpdateDate(creationDate);

        return filmListRepository.save(saved);
    }

    public FilmList getListById(Long id) {
        return filmListRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("No list found with the provided ID: %s", id)
        ));
    }

    //todo add you watched % of list

    //todo Popularity All Time This Week This Month This Year
    public List<FilmList> getAllFilmLists() {
        return filmListRepository.findAll();
    }

    //todo filter by privacy
    //WHEN UPDATED
    //List Name
    //List Popularity
    //When Updated
    //When Published
    //Newest First
    //Oldest First
    //When Created
    //Newest First
    //Oldest First
    public List<FilmList> getAllFilmListsByUserId(Long userId) {
        return filmListRepository.findByUserId(userId);
    }

    public FilmList updateFilmList(FilmListUpdateDTO dto, Long id) {
        FilmList list = filmListRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("Cannot update film list:: No list found with the provided ID: %s", id)
        ));

        FilmList mapped = filmListMapper.mapFlmListUpdateDTOToFilmList(dto, list);
        mapped.setLastUpdateDate(LocalDate.now());

        return filmListRepository.save(mapped);
    }

    public void deleteFilmList(Long id) {
        filmListRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("Cannot delete film list:: No list found with the provided ID: %s", id)
        ));

        filmListRepository.deleteById(id);
    }

    /*public List<FilmInFilmListResponseDTO> getFilmsFromList(Map<Integer, Long> filmIds) {
        Collection<Long> idList = filmIds.values();
        Stream<Film> filmStream = filmService.getFilmsByIdList(List.copyOf(idList)).stream();

        List<Film> filmsInOrder = filterListByOrder(filmStream, "list", filmIds).toList();

        return filmListMapper.mapFilmToFilmInFilmListResponseDTO(filmsInOrder);
    }*/

    public List<FilmInFilmListResponseDTO> getFilmsFromList(List<Long> filmIds) {
        List<Film> films = filmService.getFilmsByIdList(filmIds);

        films.sort(Comparator.comparing(it -> filmIds.indexOf(it.getId())));

        return filmListMapper.mapFilmToFilmInFilmListResponseDTO(films);
    }

    public FilmList getListByIdWithParams(Map<String, String> params, Long id) {
        return filmListRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("No list found with the provided ID: %s", id)
        ));
    }

    private Stream<Film> filterListByFilmName(Stream<Film> films, String name) {
        return null;
    }

    private Stream<Film> filterListByFilmPopularity(Stream<Film> films, String popularity) {
        return null;
    }

    private Stream<Film> filterListByFilmAddDate(Stream<Film> films, String whenAdded) {
        switch (whenAdded) {
            case "newest" -> {

            }
            case "earliest" -> {

            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse whenAdded parameter, should be \"list\" or \"reverse\"");
            }
        }
        return null;
    }

    private Stream<Film> filterListByOrder(Stream<Film> films, String order, Map<Integer, Long> filmIds) {
        switch (order) {
            case "list" -> {
                return films.sorted((o1, o2) ->
                        filmIds.entrySet()
                                .stream()
                                .filter(entry -> o1.getId().equals(entry.getValue()))
                                .map(Map.Entry::getKey).findFirst().get()
                                .compareTo(
                                        filmIds.entrySet()
                                                .stream()
                                                .filter(entry -> o2.getId().equals(entry.getValue()))
                                                .map(Map.Entry::getKey).findFirst().get()));
            }
            case "reverse" -> {
                List<Film> filmList = new ArrayList<>(films.sorted((o1, o2) ->
                        filmIds.entrySet()
                                .stream()
                                .filter(entry -> o1.getId().equals(entry.getValue()))
                                .map(Map.Entry::getKey).findFirst().get()
                                .compareTo(
                                        filmIds.entrySet()
                                                .stream()
                                                .filter(entry -> o2.getId().equals(entry.getValue()))
                                                .map(Map.Entry::getKey).findFirst().get())).toList());
                Collections.reverse(filmList);
                return filmList.stream();
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse order parameter, should be \"list\" or \"reverse\"");
            }
        }
    }
}
