INSERT INTO users (username, password)
VALUES ('scotty', '1111'), ('jimmy', '1111');

INSERT INTO activity_on_film (film_id, user_id, is_liked, rating, is_watched, is_in_watchlist, watchlist_add_date)
VALUES
(1, 1, false, 3.5, true, false, '2024-07-19'),
(8, 1, true, 5.0, true, false, '2019-08-19'),
(5, 1, true, 4.5, true, false, '2024-06-15'),
(2, 1, false, 4.0, true, false, '2024-07-17'),
(7, 1, true, 3.0, true, false, '2024-07-19'),
(6, 1, true, 0.0, false, true, '2024-07-18'),
(4, 1, true, 0.0, false, true, '2024-07-19'),
(3, 1, true, 0.0, false, true, '2024-07-17');

INSERT INTO reviews (film_id, user_id, is_liked, rating, content, creation_date, watched_on_date)
VALUES
(1, 1, false, 3.5, 'gosling and robbie wow!', '2024-07-19', '2024-07-19'),
(1, 2, false, 1.0, 'review', '2024-07-11', '2021-07-19'),
(2, 1, false, 4.0, 'korean cinematography', '2024-07-17', '2024-07-17'),
(2, 2, false, 2.5, 'review', '2024-07-12', '2021-07-19'),
(3, 2, false, 2.0, 'review', '2024-07-14', '2021-07-19'),
(4, 2, false, 2.5, 'review', '2024-07-15', '2021-07-19'),
(5, 1, true, 4.5, 'gosling and stone wow!', '2024-06-15', '2024-06-15'),
(5, 2, false, 3.0, 'review', '2024-07-10', '2021-07-19'),
(6, 2, false, 3.5, 'review', '2024-04-19', '2021-07-19'),
(7, 1, true, 3.0, 'literally me again', '2024-07-19', '2024-07-19'),
(7, 2, false, 4.0, 'review', '2024-05-19', '2021-07-19'),
(7, 2, false, 5.0, 'review', '2024-05-19', '2021-07-19'),
(7, 2, false, 4.0, 'review', '2024-05-19', '2021-07-19'),
(8, 1, true, 5.0, 'literally me', '2019-08-19', '2019-08-19'),
(8, 2, false, 4.5, 'review', '2024-03-19', '2021-07-19'),
(9, 2, false, 5.0, 'review', '2023-07-19', '2021-07-19'),
(10, 2, false, 5.0, 'review', '2022-07-19', '2021-07-19');

