create table quote
(
  id int auto_increment primary key,
  name varchar(255) null
);

CREATE TABLE user (
    id int auto_increment primary key,
    email varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    username varchar(255) DEFAULT NULL
);


