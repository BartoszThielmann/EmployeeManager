CREATE DATABASE IF NOT EXISTS employee_schema;
USE employee_schema;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS workspace;
DROP TABLE IF EXISTS office;

###################################################
##############SETUP EMPLOYEE TABLE#################
###################################################

CREATE TABLE employee(
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  email varchar(45) NOT NULL UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


###################################################
###########SETUP USERS AND AUTHORITIES#############
###################################################

CREATE TABLE users(
	username varchar(50) NOT NULL PRIMARY KEY,
	`password` varchar(68) NOT NULL,
	enabled BOOLEAN NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE authorities (
	username varchar(50) NOT NULL,
	authority varchar(50) NOT NULL,
	CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

# username: admin; password: admin
# username: user; password: user
INSERT INTO users(username, `password`, enabled)
VALUES
("admin", "{bcrypt}$2a$12$BfaGxmubstltGghjKfh1IOyapbYuwflkv9nrf3Jz4vnF0JDxLxx3e", true),
("user", "{bcrypt}$2a$12$P9Q0ttdau9M6DAruSiuu0eq0eiUlnwQYEgPz9xK5.gQmtEgR97pIW", true);

INSERT INTO authorities(username, authority)
VALUES
("admin", "ROLE_ADMIN"),
("admin", "ROLE_USER"),
("User", "ROLE_USER");


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