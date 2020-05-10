CREATE TABLE author (
    id int(11) NOT NULL,
    country varchar(3) DEFAULT NULL,
    firstname varchar(50) NOT NULL,
    surname varchar(50) NOT NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE category (
    id int(11) NOT NULL,
    name varchar(50) NOT NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE quote (
    id int(11) NOT NULL,
    global bit(1) NOT NULL,
    quote varchar(1000) NOT NULL,
    author_id int(11) NOT NULL,
    user_id int(11) NOT NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE quote_category (
    quote_id int(11) NOT NULL,
    category_id int(11) NOT NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE quote_score (
    quote_id int(11) NOT NULL,
    user_id int(11) NOT NULL,
    score tinyint(4) NOT NULL
) DEFAULT CHARSET=utf8mb4;

CREATE TABLE user (
    id int(11) NOT NULL,
    email varchar(100) DEFAULT NULL,
    firstname varchar(50) DEFAULT NULL,
    password varchar(100) DEFAULT NULL,
    surname varchar(50) DEFAULT NULL,
    username varchar(35) NOT NULL
) DEFAULT CHARSET=utf8mb4;

ALTER TABLE author
    ADD PRIMARY KEY (id),
    ADD UNIQUE KEY UKr1vqikecxbxj0rjrhmb2kbrrn (firstname,surname);

ALTER TABLE category
    ADD PRIMARY KEY (id),
    ADD UNIQUE KEY UK_46ccwnsi9409t36lurvtyljak (name);

ALTER TABLE quote
    ADD PRIMARY KEY (id),
    ADD KEY FK55f8tgrl40w4xdhsfvgy2ve4m (author_id),
    ADD KEY FKmc4aq5ngrmytr53brbl9061md (user_id);

ALTER TABLE quote_category
    ADD KEY FKcbyjhdc91pn0lxsj066vb03nu (category_id),
    ADD KEY FKso6yp0aqm4g2jqg5b8yef49gf (quote_id);

ALTER TABLE quote_score
    ADD PRIMARY KEY (quote_id,user_id),
    ADD KEY FK7dxkti8jhnurqs4yaol6wu7h8 (user_id);

ALTER TABLE user
    ADD PRIMARY KEY (id),
    ADD UNIQUE KEY UK_sb8bbouer5wak8vyiiy4pf2bx (username),
    ADD UNIQUE KEY UK_ob8kqyqqgmefl0aco34akdtpe (email);

ALTER TABLE author
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE category
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE quote
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

ALTER TABLE user
    MODIFY id int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;