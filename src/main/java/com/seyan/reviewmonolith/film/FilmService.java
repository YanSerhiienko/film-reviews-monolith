package com.seyan.reviewmonolith.film;

import com.seyan.reviewmonolith.exception.film.FilmNotFoundException;
import com.seyan.reviewmonolith.exception.film.SortingParametersException;
import com.seyan.reviewmonolith.exception.profile.ProfileNotFoundException;
import com.seyan.reviewmonolith.film.dto.FilmCreationDTO;
import com.seyan.reviewmonolith.film.dto.FilmMapper;
import com.seyan.reviewmonolith.film.dto.FilmUpdateDTO;
import com.seyan.reviewmonolith.profile.Profile;
import com.seyan.reviewmonolith.profile.ProfileRepository;
import com.seyan.reviewmonolith.review.ReviewService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    private final ReviewService reviewService;
    //private final UserService userService;

    private final ProfileRepository profileRepository;


    public FilmService(FilmRepository filmRepository, FilmMapper filmMapper, @Lazy ReviewService reviewService, ProfileRepository profileRepository) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
        this.reviewService = reviewService;
        this.profileRepository = profileRepository;
    }


    //todo add check for existing director and cast members in db
    //todo actually director is not updating
    public Film createFilm(FilmCreationDTO dto) {
        Film film = filmMapper.mapFilmCreationDTOToFilm(dto);

        if (dto.directorId() != null) {
            Profile director = profileRepository.findById(dto.directorId()).orElseThrow(() -> new ProfileNotFoundException(
                    String.format("No director profile found with the provided ID: %s", dto.directorId())
            ));
            film.setDirector(director);
        }

        if (dto.castIdList() != null) {
            //todo create this as a method (also use in add cast)
            List<Profile> foundInDatabaseCastList = profileRepository.findAllById(dto.castIdList());
            if (dto.castIdList().size() != foundInDatabaseCastList.size()) {
                List<Long> foundCastIdList = foundInDatabaseCastList.stream().map(Profile::getId).toList();
                dto.castIdList().removeAll(foundCastIdList);
                throw new ProfileNotFoundException(
                        String.format("Some profiles from the provided ID list were not found : %s", dto.castIdList()));
            }
            film.getCast().addAll(foundInDatabaseCastList);
            //foundCastList.forEach(film.getCast()::add);
        }

        Film withUrl = createUrl(film);
        return filmRepository.save(withUrl);
    }

    //todo like count
    public Integer updateLikeCount(boolean isLiked) {
        //filmRepository.updateLike if true +1 / if false -1
        return null;
    }

    public Double updateAvgRating(Long filmId, Double rating) {
        return null;
    }

    private Film createUrl(Film film) {
        //todo fix count when films were deleted
        String[] title = film.getTitle().split(" ");
        StringBuilder urlBuilder = new StringBuilder();

        for (String s : title) {
            urlBuilder.append(s).append("-");
        }

        String url = urlBuilder.append(film.getReleaseDate().getYear()).toString();

        int similarUrlCount = filmRepository.countByUrlContaining(url);

        if (similarUrlCount > 0) {
            urlBuilder.append("-").append(++similarUrlCount);
            film.setUrl(urlBuilder.toString());
            return film;
        }

        film.setUrl(url);
        return film;
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", id)));
    }

    //TODO CHECK UPDATE PROFILE WITHOUT CASCADE
//    public Film updateFilm(FilmUpdateDTO dto) {
//        Film film = filmRepository.findById(dto.id()).orElseThrow(() -> new FilmNotFoundException(
//                String.format("No film found with the provided ID: %s", dto.id())));
//
//        Film mapped = filmMapper.mapFilmUpdateDTOToFilm(dto, film);
//
//        if (dto.title() != null && !dto.title().equals(film.getTitle())) {
//            Film mappedWithUrl = createUrl(mapped);
//            return filmRepository.save(mappedWithUrl);
//        }
//
//        return filmRepository.save(mapped);
//    }

    public Film updateFilm(FilmUpdateDTO dto, Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", id)));

        Film mapped = filmMapper.mapFilmUpdateDTOToFilm(dto, film);

        if (dto.title() != null && !dto.title().equals(film.getTitle())) {
            Film mappedWithUrl = createUrl(mapped);
            return filmRepository.save(mappedWithUrl);
        }

        return filmRepository.save(mapped);
    }

    //todo addCastMember
    /*public Film addCastMember(Long profileId, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", filmId)));

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(
                String.format("No profile found with the provided ID: %s", profileId)
        ));

        film.getCast().add(profile);
        return filmRepository.save(film);
    }*/

    //todo some profiles were not found with provided id's
    public Film addCastMember(List<Long> profileIdList, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", filmId)));

        List<Profile> profileList = profileRepository.findAllById(profileIdList);

        if (profileIdList.size() != profileList.size()) {
            List<Long> foundProfileIdList = profileList.stream().map(Profile::getId).toList();
            profileIdList.removeAll(foundProfileIdList);
            throw new ProfileNotFoundException(
                    String.format("Some profiles from the provided ID list were not found : %s", profileIdList));
        }

        film.getCast().addAll(profileList);
        return filmRepository.save(film);
    }

    //todo removeCastMember
    /*public Film removeCastMember(Long profileId, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", filmId)));

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(
                String.format("No profile found with the provided ID: %s", profileId)
        ));

        film.getCast().remove(profile);
        return filmRepository.save(film);
    }*/

    public Film removeCastMember(List<Long> profileIdList, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", filmId)));

        List<Profile> profileList = profileRepository.findAllById(profileIdList);

        profileList.forEach(film.getCast()::remove);
        return filmRepository.save(film);
    }

    public Film updateDirector(Long profileId, Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", filmId)));

        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new ProfileNotFoundException(
                String.format("No profile found with the provided ID: %s", profileId)
        ));

        film.setDirector(profile);

        return filmRepository.save(film);
    }

    public void updateWatchedCount(boolean update) {
        //todo repo method
    }

    public void deleteFilm(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided ID: %s", id)));
        filmRepository.deleteById(id);
    }

    /*public Film getFilmByTitle(String title) {
        return filmRepository.findByTitle(title).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided title: %s", title)
        ));
    }*/

    public List<Film> getAllFilmsByTitle(String title) {
        //String[] splitTitle = title.split("+");
        String[] split = title.split("-");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]).append(" ");
        }
        String parsedTitle = builder.append(split[split.length - 1]).toString();
        System.out.println("_____________________________________________parsedTitle = " + parsedTitle);
        return filmRepository.findByTitleContaining(parsedTitle);
    }

    public Film getFilmByUrl(String filmUrl) {
        //todo change throw msg???
        return filmRepository.findByUrl(filmUrl).orElseThrow(() -> new FilmNotFoundException(
                String.format("No film found with the provided url: %s", filmUrl)
        ));
    }

    public List<Film> getAllFilmsWithParams(Map<String, String> params, Long userId) {
        //todo get id from authentication principal(?)
        Stream<Film> stream;
        if (params.containsKey("popular")) {
            stream = filterFilmsByPopularity(params.get("popular"));
        } else {
            stream = filmRepository.findAll().stream();
        }

        if (params.containsKey("decade")) {
            stream = filterFilmsByDecade(stream, params.get("decade"));
        }

        if (params.containsKey("genre")) {
            stream = filterFilmsByGenre(stream, params.get("genre"));
        }

        if (params.containsKey("name")) {
            stream = filterFilmsByName(stream, params.get("name"));
        }

        if (params.containsKey("release")) {
            stream = filterFilmsByReleaseDate(stream, params.get("release"));
        }

        if (params.containsKey("rating")) {
            stream = filterFilmsByRating(stream, params.get("rating"));
        }

        if (params.containsKey("your-rating")) {
            stream = filterFilmsByYourRating(stream, params.get("your-rating"), userId);
        }

        if (params.containsKey("length")) {
            stream = filterFilmsByLength(stream, params.get("length"));
        }

        return stream.collect(Collectors.toList());
    }

    private Stream<Film> filterFilmsByName(Stream<Film> stream, String name) {
        switch (name) {
            case "asc" -> {
                return stream.sorted(Comparator.comparing(Film::getTitle));
            }
            case "desc" -> {
                return stream.sorted(Comparator.comparing(Film::getTitle).reversed());
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse name parameter, should be \"asc\" or \"desc\"");
            }
        }
    }

    private Stream<Film> filterFilmsByLength(Stream<Film> stream, String length) {
        switch (length) {
            case "shortest" -> {
                return stream.sorted(Comparator.comparing(Film::getRunningTimeMinutes));
            }
            case "longest" -> {
                return stream.sorted(Comparator.comparing(Film::getRunningTimeMinutes).reversed());
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse length parameter, should be \"shortest\" or \"longest\"");
            }
        }
    }

    private Stream<Film> filterFilmsByYourRating(Stream<Film> stream, String rating, Long userId) {
        //todo rated films should be sorted and go first
        //get rated and sort

        List<Long> filmIdList = reviewService.getFilmIdFromReviewsWithFilmRatingByUserId(userId);

        //Stream<Film> filmsWithLikeStream = stream.filter(f -> filmIdList.contains(f.getId()));
        //Stream<Film> filmsWithoutLikeStream = stream.filter(f -> !filmIdList.contains(f.getId()));

        //stream divided on two collections: with & without rating
        Map<Boolean, List<Film>> filmLists = stream
                .collect(Collectors.partitioningBy(film -> filmIdList.contains(film.getId())));

        List<Film> filmsWithRating = filmLists.get(true);
        List<Film> filmsWithoutRating = filmLists.get(false);

        switch (rating) {
            case "highest" -> {
                return Stream.concat(
                        filmsWithRating.stream().sorted(Comparator.comparing(Film::getAvgRating).reversed()),
                        filmsWithoutRating.stream().sorted(Comparator.comparing(Film::getWatchedCount)));
                //return stream.sorted(Comparator.comparing(Film::getRating).reversed());
            }
            case "lowest" -> {
                return Stream.concat(
                        filmsWithRating.stream().sorted(Comparator.comparing(Film::getAvgRating)),
                        filmsWithoutRating.stream().sorted(Comparator.comparing(Film::getWatchedCount)));
                //return stream.sorted(Comparator.comparing(Film::getRating));
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse rating parameter, should be \"highest\" or \"lowest\"");
            }
        }
    }

    private Stream<Film> filterFilmsByGenre(Stream<Film> stream, String genre) {
        //todo rework if genre only one for entity
        //Genre filmGenre = Genre.valueOf(genre);

        //return stream.filter(it -> it.getGenres().toString().toLowerCase().contains(genre));
        return stream.filter(it -> it.getGenre().equals(Genre.valueOf(genre)));
    }

    private Stream<Film> filterFilmsByDecade(Stream<Film> stream, String decade) {
        if (decade.equals("upcoming")) {
            LocalDate now = LocalDate.now();
            return stream.filter(it -> it.getReleaseDate().isAfter(now));
        }
        //todo year is not a decade!!!
        int decadeParsed = Integer.parseInt(decade);
        return stream.filter(it -> it.getReleaseDate().getYear() == decadeParsed);
    }

    private Stream<Film> filterFilmsByPopularity(String popularity) {
        //todo review service find by creation date
        //todo if review service is down - reply with all films
        //List<Review> reviews = reviewService.getPopularReviews(popularity);
        //List<Long> filmIdList = reviews.stream().map(Review::getFilmId).collect(Collectors.toList());
        List<Long> filmIdList = reviewService.getFilmIdFromPopularReviews(popularity);
        return filmRepository.findAllById(filmIdList).stream();
    }

    private Stream<Film> filterFilmsByReleaseDate(Stream<Film> stream, String release) {
        switch (release) {
            case "newest" -> {
                return stream.sorted(Comparator.comparing(Film::getReleaseDate).reversed());
            }
            case "earliest" -> {
                return stream.sorted(Comparator.comparing(Film::getReleaseDate));
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse release parameter, should be \"earliest\" or \"newest\"");
            }
        }
    }

    private Stream<Film> filterFilmsByRating(Stream<Film> stream, String rating) {
        switch (rating) {
            case "highest" -> {
                return stream.sorted(Comparator.comparing(Film::getAvgRating).reversed());
            }
            case "lowest" -> {
                return stream.sorted(Comparator.comparing(Film::getAvgRating));
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse rating parameter, should be \"highest\" or \"lowest\"");
            }
        }
    }

    //todo based on likes - get films with similar genre(???)
    //todo based on related - get sequels(films from one collection) and then with genre
    /*private Stream<Film> filterFilmsByYourInterest(Stream<Film> stream, String interest, Long userId) {
        List<Long> likedFilms = userService.getLikedFilms(userId);
        Map<Boolean, List<Film>> filmLists = stream
                .collect(Collectors.partitioningBy(film -> likedFilms.contains(film.getId())));

        List<Genre> likedFilmsGenre = filmLists.get(true).stream().map(Film::getGenre).distinct().toList();
        List<Film> filmsWithoutRating = filmLists.get(false);

        switch (interest) {
            case "liked" -> {
                return stream.sorted(Comparator.comparing(Film::getRunningTimeMinutes));
            }
            case "related" -> {
                return stream.sorted(Comparator.comparing(Film::getRunningTimeMinutes).reversed());
            }
            default -> {
                throw new SortingParametersException(
                        "Could not parse length parameter, should be \"shortest\" or \"longest\"");
            }
        }
    }*/
}
