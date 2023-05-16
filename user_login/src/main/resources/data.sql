DROP TABLE IF EXISTS lusers;

CREATE TABLE lusers (
id int NOT NULL AUTO_INCREMENT,
username NVARCHAR(255),
password NVARCHAR(255),
role NVARCHAR(255),
PRIMARY KEY (id)
);

INSERT INTO lusers (username, password, role) VALUES ('admin', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.','admin');
