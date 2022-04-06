CREATE DATABASE PhuLuc;
USE PhuLuc;

CREATE TABLE Catalog(
    id INT AUTO_INCREMENT PRIMARY KEY ,
    name VARCHAR(100)
);

INSERT INTO Catalog(name) VALUES ('Coffee');
INSERT INTO Catalog(name) VALUES ('Travel');
