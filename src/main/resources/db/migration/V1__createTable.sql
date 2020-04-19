CREATE TABLE `author` (
                          `id` int(11) NOT NULL,
                          `biography` varchar(255) DEFAULT NULL,
                          `first_name` varchar(255) DEFAULT NULL,
                          `sure_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB default charset=utf8;

CREATE TABLE `category` (
                            `id` int(11) NOT NULL,
                            `description` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB default charset=utf8;

CREATE TABLE `quote` (
                         `id` int(11) NOT NULL,
                         `quote` varchar(255) DEFAULT NULL,
                         `author_id` int(11) NOT NULL,
                         `category_id` int(11) NOT NULL,
                         `user_id` int(11) NOT NULL
) ENGINE=InnoDB default charset=utf8;

CREATE TABLE `user` (
                        `id` int(11) NOT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `name` varchar(255) DEFAULT NULL,
                        `firstname` varchar(255) NOT NULL,
                        `login` varchar(255) NOT NULL,
                        `surename` varchar(255) NOT NULL
) ENGINE=InnoDB default charset=utf8;

ALTER TABLE `author` ADD PRIMARY KEY (`id`);
ALTER TABLE `category` ADD PRIMARY KEY (`id`);

ALTER TABLE `quote` ADD PRIMARY KEY (`id`),
    ADD KEY `FK55f8tgrl40w4xdhsfvgy2ve4m` (`author_id`),
    ADD KEY `FK4adw1kt9vyl6qtaxlkym1fb9q` (`category_id`),
    ADD KEY `FKmc4aq5ngrmytr53brbl9061md` (`user_id`);

ALTER TABLE `user`
    ADD PRIMARY KEY (`id`);
COMMIT;
