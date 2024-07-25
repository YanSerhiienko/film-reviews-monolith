package com.seyan.reviewmonolith.filmList;

import com.seyan.reviewmonolith.exception.film.SortingParametersException;
import com.seyan.reviewmonolith.exception.filmList.FilmListNotFoundException;
import com.seyan.reviewmonolith.film.Film;
import com.seyan.reviewmonolith.film.FilmService;
import com.seyan.reviewmonolith.filmList.dto.FilmInFilmListResponseDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListCreationDTO;
import com.seyan.reviewmonolith.filmList.dto.FilmListMapper;
import com.seyan.reviewmonolith.filmList.dto.FilmListUpdateDTO;
import com.seyan.reviewmonolith.filmList.entry.ListEntry;
import com.seyan.reviewmonolith.filmList.entry.ListEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FilmListService {
    private final FilmListRepository filmListRepository;
    private final FilmListMapper filmListMapper;
    private final ListEntryRepository entryRepository;

    //todo replace with controller calls
    private final FilmService filmService;

    @Transactional
    public FilmList createList(FilmListCreationDTO dto) {
        LocalDate creationDate = LocalDate.now();

        FilmList mapped = filmListMapper.mapFilmListCreationDTOToFilmList(dto);
        FilmList saved = filmListRepository.save(mapped);

        System.out.println("saved = " + saved);


        List<ListEntry> listEntries = mapListUniqueEntries(saved.getId(), dto.filmIds(), creationDate);
        List<ListEntry> entries = listEntries.stream().peek(it -> it.setEntryOrder((long) listEntries.indexOf(it))).toList();

        //Map<Integer, ListEntry> orderedEntries = filmListMapper.mapListEntriesToOrderedMap(listEntries);

        //saved.setFilmIds(orderedEntries);
        //saved.getFilmIds().addAll(listEntries);
        entryRepository.saveAll(entries);
        saved.setCreationDate(creationDate);
        saved.setLastUpdateDate(creationDate);

        System.out.println("saved = " + saved);

        return filmListRepository.save(saved);
    }

    /*public FilmList createList(FilmListCreationDTO dto) {
        LocalDate creationDate = LocalDate.now();

        FilmList mapped = filmListMapper.mapFilmListCreationDTOToFilmList(dto);
        mapped.setCreationDate(creationDate);

        //List<ListEntry> listEntries = mapListUniqueEntries(dto.filmIds(), creationDate);
        List<ListEntry> listEntries = mapListUniqueEntries(dto.filmIds(), creationDate);

        mapped.setEntries(listEntries);

        return filmListRepository.save(mapped);
    }*/

    private List<ListEntry> mapListUniqueEntries(Long listId, List<Long> filmIds, LocalDate addDate) {
        List<Long> filmIdsUnique = filmIds.stream().distinct().toList();
        return filmListMapper.mapFilmIdsToListEntries(listId, filmIdsUnique, addDate);
    }

    /*private List<ListEntry> mapListUniqueEntries(List<Long> filmIds, LocalDate addDate) {
        List<Long> filmIdsUnique = filmIds.stream().distinct().toList();
        return filmListMapper.mapFilmIdsToListEntries(filmIdsUnique, addDate);
    }*/





    //todo add checks for if present and unique/ordering
    @Transactional
    public FilmList updateFilmList(FilmListUpdateDTO dto, Long id) {
        FilmList list = filmListRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("Cannot update film list:: No list found with the provided ID: %s", id)
        ));

        LocalDate updateDate = LocalDate.now();
        FilmList mapped = filmListMapper.mapFlmListUpdateDTOToFilmList(dto, list);
        mapped.setLastUpdateDate(updateDate);

        if (dto.filmIds() != null) {
            //filmListRepository.clearListEntriesRelationBeforeUpdate(list.getId());
            List<Long> filmIdsUpdate = dto.filmIds().stream().distinct().toList();

            List<ListEntry> existingEntries = entryRepository.findByListId(list.getId());


            List<ListEntry> existingToLeftEntries = existingEntries.stream().filter(it -> filmIdsUpdate.contains(it.getFilmId())).toList();
            List<Long> existingToLeftFilmIds = existingToLeftEntries.stream().map(it -> it.getFilmId()).toList();



            Map<Boolean, List<ListEntry>> es = existingEntries.stream()
                    .collect(Collectors.partitioningBy(it -> filmIdsUpdate.contains(it.getFilmId())));

            List<Long> newFilmIds = filmIdsUpdate.stream().filter(it -> !existingToLeftFilmIds.contains(it)).toList();
            List<ListEntry> newEntries = mapListUniqueEntries(list.getId(), newFilmIds, updateDate);


            //List<Long> presentIds = existingEntries.stream().map(ListEntry::getFilmId).toList();

            List<ListEntry> updatedEntries = Stream.concat(existingToLeftEntries.stream(), newEntries.stream())
                    .peek(it -> it.setEntryOrder((long) filmIdsUpdate.indexOf(it.getFilmId())))
                    .sorted(Comparator.comparing(ListEntry::getEntryOrder)).toList();

            System.out.println("updatedEntries = " + updatedEntries);

            List<Long> list1 = es.get(false).stream().map(ListEntry::getFilmId).toList();

            entryRepository.deleteByFilmIdIn(list1);
            //entryRepository.deleteEntriesByListId(list.getId());
            entryRepository.saveAll(updatedEntries);
        }

        return filmListRepository.save(mapped);
    }

    /*@Transactional
    public FilmList updateFilmList(FilmListUpdateDTO dto, Long id) {
        FilmList list = filmListRepository.findById(id).orElseThrow(() -> new FilmListNotFoundException(
                String.format("Cannot update film list:: No list found with the provided ID: %s", id)
        ));

        System.out.println("list.toString() = " + list.toString());

        LocalDate updateDate = LocalDate.now();
        FilmList mapped = filmListMapper.mapFlmListUpdateDTOToFilmList(dto, list);
        mapped.setLastUpdateDate(updateDate);

        if (dto.filmIds() != null) {
            //filmListRepository.clearListEntriesRelationBeforeUpdate(list.getId());
            List<Long> filmIdsUpdate = dto.filmIds().stream().distinct().toList();

            List<ListEntry> presentToLeftEntries = list.getFilmIds().stream().filter(it -> filmIdsUpdate.contains(it.getFilmId())).toList();
            List<Long> presentToLeftFilmIds = presentToLeftEntries.stream().map(it -> it.getFilmId()).toList();

            List<Long> newFilmIds = filmIdsUpdate.stream().filter(it -> !presentToLeftFilmIds.contains(it)).toList();
            List<ListEntry> newEntries = mapListUniqueEntries(list.getId(), newFilmIds, updateDate);


            List<Long> presentIds = list.getFilmIds().stream().map(ListEntry::getFilmId).toList();

            if(!filmIdsUpdate.equals(presentIds) || !newEntries.isEmpty()) {
                List<ListEntry> updatedEntries = Stream.concat(presentToLeftEntries.stream(), newEntries.stream())
                        .sorted(Comparator.comparing(it -> filmIdsUpdate.indexOf(it.getFilmId()))).toList();
                filmListRepository.save(mapped);
                //filmListRepository.clearEntriesBeforeUpdate(list.getId());
                List<ListEntry> entriesToRemove = mapped.getFilmIds();
                mapped.getFilmIds().removeAll(entriesToRemove);
                mapped.getFilmIds().addAll(updatedEntries);
            }


            //Map<Integer, ListEntry> orderedUpdatedEntries = filmListMapper.mapListEntriesToOrderedMap(updatedEntries);

            *//*mapped.setFilmIds(orderedUpdatedEntries);
            System.out.println("orderedUpdatedEntries = " + orderedUpdatedEntries);*//*

            //System.out.println("orderedUpdatedEntries = " + updatedEntries);
        }


        return filmListRepository.save(mapped);
        //return filmListRepository.save(list);
    }*/

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
