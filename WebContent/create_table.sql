-- The Begin of the script
CREATE DATABASE IF NOT EXISTS `cs122b` DEFAULT CHARACTER SET latin1;
USE `cs122b`;

DROP TABLE IF EXISTS `ratings`;
DROP TABLE IF EXISTS `sales`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `creditcards`;
DROP TABLE IF EXISTS `games_on_platform`;
DROP TABLE IF EXISTS `platforms`;
DROP TABLE IF EXISTS `characters_in_games`;
DROP TABLE IF EXISTS `characters`;
DROP TABLE IF EXISTS `games`;
DROP TABLE IF EXISTS `employees`;
DROP PROCEDURE IF EXISTS `add_game`;

CREATE TABLE `games` (
  `gid` INTEGER NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(1000) NOT NULL,
  `year` INTEGER NOT NULL,
  `lead_designer` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`gid`),
  FULLTEXT (title)
);

CREATE TABLE `employees` (
`email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
`fullname` varchar(100),
  PRIMARY KEY (`email`)
);

CREATE TABLE `characters` (
  `chid` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  `first_appearance` VARCHAR(1000) DEFAULT NULL,
  PRIMARY KEY (`chid`)
);

CREATE TABLE `characters_in_games` (
  `chid` INTEGER NOT NULL,
  `gid` INTEGER NOT NULL,
  PRIMARY KEY (`chid`,`gid`),
  FOREIGN KEY (`gid`) REFERENCES games (`gid`) ON DELETE CASCADE,
  FOREIGN KEY (`chid`) REFERENCES characters (`chid`) ON DELETE CASCADE
);

CREATE TABLE `platforms` (
  `pid` INTEGER NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`pid`)
);

CREATE TABLE `games_on_platform` (
  `pid` INTEGER NOT NULL,
  `gid` INTEGER NOT NULL,
  PRIMARY KEY (`gid`,`pid`),
  FOREIGN KEY (`gid`) REFERENCES games (`gid`) ON DELETE CASCADE,
  FOREIGN KEY (`pid`) REFERENCES platforms (`pid`) ON DELETE CASCADE
);

CREATE TABLE `creditcards` (
  `ccid` VARCHAR(20) NOT NULL,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `expire_date` DATE NOT NULL,
  PRIMARY KEY (`ccid`)
);

CREATE TABLE `customers` (
  `cid` INTEGER NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(30) NOT NULL,
  `last_name` VARCHAR(30) NOT NULL,
  `crid` VARCHAR(20) NOT NULL,
  `address` VARCHAR(200) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`cid`),
  FOREIGN KEY (`crid`) REFERENCES creditcards (`ccid`) ON DELETE CASCADE
);

CREATE TABLE `sales` (
  `sid` INTEGER NOT NULL AUTO_INCREMENT,
  `cid` INTEGER NOT NULL,
  `gid` INTEGER NOT NULL,
  `sale_date` DATE NOT NULL,
  PRIMARY KEY (`sid`),
  FOREIGN KEY (`cid`) REFERENCES customers (`cid`) ON DELETE CASCADE,
  FOREIGN KEY (`gid`) REFERENCES games (`gid`) ON DELETE CASCADE
);

CREATE TABLE `ratings` (
  `gid` INTEGER NOT NULL,
  `rating` FLOAT NOT NULL,
  `number_ratings` INTEGER NOT NULL,
  PRIMARY KEY (`gid`),
  FOREIGN KEY (`gid`) REFERENCES games (`gid`)
);

DELIMITER //
CREATE PROCEDURE
 add_game(gname VARCHAR(1000),year int,dname VARCHAR(300),cname VARCHAR(30),first_appear VARCHAR(1000),pname VARCHAR(30))
BEGIN
 INSERT INTO games (title,year,lead_designer) VALUES (gname,year,dname);
 INSERT INTO characters (name,first_appearance) SELECT * FROM (SELECT cname,first_appear) AS tmp1 WHERE NOT EXISTS (
 SELECT name,first_appearance FROM characters WHERE name=cname AND first_appearance=first_appear) LIMIT 1;
 INSERT INTO platforms (name) SELECT * FROM (SELECT pname) AS tmp2 WHERE NOT EXISTS (
 SELECT name FROM platforms WHERE name=pname) LIMIT 1;
 INSERT INTO characters_in_games VALUES((SELECT chid from characters where name=cname AND first_appearance=first_appear LIMIT 1),(SELECT gid FROM games WHERE title=gname AND year=year AND lead_designer=dname));
 INSERT INTO games_on_platform VALUES((SELECT pid from platforms where name = pname),(SELECT gid FROM games WHERE title=gname AND year=year AND lead_designer=dname));
 INSERT INTO ratings VALUES (((SELECT gid FROM games WHERE title=gname AND year=year AND lead_designer=dname)),1,100);
END//
DELIMITER ;
