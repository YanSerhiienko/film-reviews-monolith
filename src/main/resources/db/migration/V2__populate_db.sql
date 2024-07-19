INSERT INTO profiles (name, url, biography)
VALUES
('Need-to Add', 'Need-to Add',
 'Need to add director, obviously.'),
('Greta Gerwig', 'greta-gerwig',
 'Greta Gerwig is an American actress, playwright, screenwriter, and director based in NY.'),
('Bong Joon-ho', 'bong-joon-ho',
 'Bong Joon-ho (Korean: 봉준호), born September 14, 1969, is a South Korean film director, producer and screenwriter.'),
('Daniel Scheinert', 'daniel-scheinert',
 'Daniel Scheinert is best known as the redneck half of the filmmaking duo DANIELS along with Daniel Kwan.'),
('David Fincher', 'david-fincher',
 'David Andrew Leo Fincher (born August 28, 1962) is an American film director.');



INSERT INTO films (
    title, genre, description,
    release_date, url, avg_rating,
    running_time_minutes, director_id,
    watched_count, like_count, list_count)
VALUES
('Barbie', 'COMEDY', 'Barbie and Ken are having the time of their lives.',
 '2023-07-06', 'barbie-2023', 0, 114, 2, 0, 0, 0),
('Parasite', 'DRAMA', 'All unemployed, Ki-taek’s family takes peculiar interest in the wealthy and glamorous Parks for their livelihood until they get entangled in an unexpected incident.',
 '2019-05-21', 'parasite-2019', 0, 133, 3, 0, 0, 0),
('Everything Everywhere All at Once', 'ADVENTURE', 'THE UNIVERSE IS SO MUCH BIGGER THAN YOU REALIZE.',
 '2022-03-11', 'everything-everywhere-all-at-once-2022', 0, 140, 4, 0, 0, 0),
('Fight Club', 'DRAMA', 'A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy.',
 '1999-09-10', 'fight-club-1999', 0, 139, 5, 0, 0, 0),
('La La Land', 'MUSIC', 'Mia, an aspiring actress, serves lattes to movie stars in between auditions and Sebastian, a jazz musician, scrapes by playing cocktail party gigs in dingy bars.',
 '2016-08-31', 'la-la-land-2016', 0, 129, 1, 0, 0, 0),
('Interstellar', 'FICTION', 'MANKIND WAS BORN ON EARTH. IT WAS NEVER MEANT TO DIE HERE.',
  '2014-10-26', 'interstellar-2014', 0, 169, 1, 0, 0, 0),
('Oppenheimer ', 'HISTORY', 'The story of J. Robert Oppenheimer’s role in the development of the atomic bomb during World War II.',
 '2023-07-11', 'oppenheimer-2023', 0, 181, 1, 0, 0, 0),
('Joker ', 'CRIME', 'During the 1980s, a failed stand-up comedian is driven insane and turns to a life of crime and chaos in Gotham City while becoming an infamous psychopathic crime figure.',
 '2019-08-31', 'joker-2019', 0, 122, 1, 0, 0, 0),
('Dune ', 'ADVENTURE', 'BEYOND FEAR, DESTINY AWAITS.',
 '2021-09-03', 'dune-2021', 0, 155, 1, 0, 0, 0),
('Pulp Fiction', 'CRIME', 'JUST BECAUSE YOU ARE A CHARACTER DOESN’T MEAN YOU HAVE CHARACTER.',
 '1994-06-21', 'pulp-fiction-1994', 0, 154, 1, 0, 0, 0);
