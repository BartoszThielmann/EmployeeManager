CREATE DATABASE IF NOT EXISTS employee_schema;
USE employee_schema;

DROP TABLE IF EXISTS `reservation`;
DROP TABLE IF EXISTS `user_detail`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `workspace`;
DROP TABLE IF EXISTS `office`;


###################################################
###########SETUP USERS AND AUTHORITIES#############
###################################################

CREATE TABLE `user`(
	`id` int NOT NULL AUTO_INCREMENT,
	`username` varchar(50) NOT NULL,
	`password` varchar(68) NOT NULL,
	`enabled` BOOLEAN NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `role`(
	`id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `user_role`(
    `user_id` INT NOT NULL,
    `role_id` INT NOT NULL,
	PRIMARY KEY(`user_id`, `role_id`),

	KEY `FK_USER_idx` (`user_id`),
	CONSTRAINT `FK_USER`
	FOREIGN KEY (`user_id`)
	REFERENCES `user` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION,

    KEY `FK_ROLE_idx` (`role_id`),
	CONSTRAINT `FK_ROLE`
	FOREIGN KEY (`role_id`)
	REFERENCES `role` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

# password: password123
INSERT INTO `user`(`id`, `username`, `password`, `enabled`)
VALUES
(1, "jsalads", "{bcrypt}$2a$12$AQIEFZ4NlYMTMhW/JU39xeRt/GeYYht7brOOOSz0hF97KtKtEkeuK", true),
(2, "ebradberry", "{bcrypt}$2a$12$AQIEFZ4NlYMTMhW/JU39xeRt/GeYYht7brOOOSz0hF97KtKtEkeuK", true),
(3, "lpaul", "{bcrypt}$2a$12$AQIEFZ4NlYMTMhW/JU39xeRt/GeYYht7brOOOSz0hF97KtKtEkeuK", true);

CREATE TABLE `user_detail`(
    `id` INT NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(45),
    `last_name` VARCHAR(45),
    `email` VARCHAR(45) UNIQUE,
    `user_id` INT,
    PRIMARY KEY(`id`),
    KEY `FK_USER_idx` (`user_id`),
    CONSTRAINT `FK_USER_BASE`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `user_detail`(`first_name`, `last_name`, `email`, `user_id`)
VALUES
("Joey", "Salads", "jsalads@bth.com", 1),
("Ethan", "Bradberry", "ebradberry@bth.com", 2),
( "Logan", "Paul", "lpaul@bth.com", 3);

INSERT INTO `role`(`id`, `name`)
VALUES
(1, "ROLE_ADMIN"),
(2, "ROLE_EMPLOYEE");

INSERT INTO `user_role`(`user_id`, `role_id`)
VALUES
(1, 1),
(2, 2),
(3, 2);

###################################################
########SETUP OFFICE AND WORKSPACE TABLES##########
###################################################

CREATE TABLE `office` (
	`id` int NOT NULL AUTO_INCREMENT,
	`street_address` varchar(100) NOT NULL,
	`city` varchar(100),
	`country` varchar(2),
	PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE `workspace` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50),
	`office_id` int,
	PRIMARY KEY(id),
	KEY `FK_OFFICE_idx` (`office_id`),
	CONSTRAINT `FK_OFFICE`
	FOREIGN KEY (`office_id`)
	REFERENCES `office` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO employee_schema.office(`street_address`, `city`, `country`)
VALUES ("Willow Street 4", "Bydgoszcz", "PL");
INSERT INTO employee_schema.workspace(`name`, `office_id`)
VALUES
("workspace 1", LAST_INSERT_ID()),
("workspace 2", LAST_INSERT_ID()),
("workspace 3", LAST_INSERT_ID());

INSERT INTO employee_schema.office(`street_address`, `city`, `country`)
VALUES ("Armrest Street 15", "Bydgoszcz", "PL");
INSERT INTO employee_schema.workspace(`name`, `office_id`)
VALUES
("workspace 1", LAST_INSERT_ID()),
("workspace 2", LAST_INSERT_ID()),
("workspace 3", LAST_INSERT_ID());

INSERT INTO employee_schema.office(`street_address`, `city`, `country`)
VALUES ("Pond Street 18", "Berlin", "DE");
INSERT INTO employee_schema.workspace(`name`, `office_id`)
VALUES
("workspace 1", LAST_INSERT_ID()),
("workspace 2", LAST_INSERT_ID()),
("workspace 3", LAST_INSERT_ID());

###################################################
#############SETUP RESERVATION TABLE###############
###################################################

CREATE TABLE reservation(
	id int NOT NULL AUTO_INCREMENT,
	`start` DATETIME(6),
	`end` DATETIME(6),
	workspace_id int,
    user_id int,
	PRIMARY KEY(id),
	KEY `FK_WORKSPACE_idx` (`workspace_id`),
	CONSTRAINT `FK_WORKSPACE`
	FOREIGN KEY (`workspace_id`)
	REFERENCES `workspace` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION,
	KEY `FK_EMPLOYEE_idx` (`user_id`),
	CONSTRAINT `FK_USER_RESERVATION`
	FOREIGN KEY (`user_id`)
	REFERENCES `user` (`id`)
	ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;