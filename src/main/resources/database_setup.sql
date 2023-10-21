CREATE DATABASE IF NOT EXISTS employee_schema;
USE employee_schema;

DROP TABLE IF EXISTS employee;

CREATE TABLE employee(
  id int NOT NULL AUTO_INCREMENT,
  first_name varchar(45) NOT NULL,
  last_name varchar(45) NOT NULL,
  email varchar(45) NOT NULL UNIQUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;