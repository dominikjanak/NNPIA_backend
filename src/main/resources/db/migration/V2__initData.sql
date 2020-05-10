INSERT INTO author (id, country, firstname, surname) VALUES
(1, 'USA', 'Albert', 'Einstein'),
(2, 'UK', 'Oscar', 'Wilde'),
(3, 'CZ', 'Miroslav', 'Horníček'),
(4, 'CZ', 'Tomáš Garrigue', 'Masaryk');

INSERT INTO category (id, name) VALUES
(1, 'Život'),
(2, 'Lidé'),
(3, 'Tajemství'),
(4, 'Chování'),
(5, 'Víra'),
(6, 'Vesmír'),
(7, 'Osamělost'),
(8, 'Oblečení'),
(9, 'Úspěch'),
(10, 'Sny');

INSERT INTO user (id, email, firstname, password, surname, username) VALUES
(1, 'public@dominikjanak.cz', 'Dominik', '$2a$10$LUJWQmfmnq6ERycvkFnZNu80Zi5TW4sP3Ebm2gowjXVd9BUnrusSy', 'Janák', 'janakdom'),
(2, 'test@dominikjanak.cz', 'test', '$2a$10$uwBNfy/Pzki8UFGOGpXZeubIZwWu6L2d6kAcCpBsSCANjZvQIpsFW', 'test', 'test'),
(3, 'user@dominikjanak.cz', 'user', '$2a$10$s0PZaL1iFoqrN0RA3nSZEOVtViSADg1dM43vgIkCgTmLhh1J5uNiW', 'user', 'user');

INSERT INTO quote (id, global, quote, author_id, user_id) VALUES
(1, b'1', 'Existuje tisíce způsobů, jak zabít čas, ale žádný, jak ho vzkřísit.', 1, 1),
(2, b'1', 'Někdo mlčí, protože neví, a někdo mlčí, protože ví své.', 2, 1),
(3, b'1', 'Tož demokracii bychom už měli, teď ještě nějaké ty demokraty.', 4, 1),
(4, b'1', 'Nejsem náročný, mám velmi jednoduchý vkus. Spokojím se vždy s tím nejlepším.', 3, 1);

INSERT INTO quote_category (quote_id, category_id) VALUES
(1, 1),
(1, 4),
(1, 9),
(2, 1),
(2, 2),
(2, 4),
(2, 9),
(3, 1),
(3, 2),
(3, 4),
(4, 1),
(4, 2),
(4, 4);

