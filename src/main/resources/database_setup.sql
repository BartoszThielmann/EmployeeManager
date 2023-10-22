CREATE DATABASE IF NOT EXISTS employee_schema;
USE employee_schema;

DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE employee(
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  email varchar(45) NOT NULL UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

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