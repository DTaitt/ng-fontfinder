CREATE TABLE Favorite (
  PRIMARY KEY (id),
  id serial,
  fontfamily VARCHAR(100) NOT NULL,
  typeface VARCHAR(100) NOT NULL,
  url VARCHAR(100) NOT NULL
);