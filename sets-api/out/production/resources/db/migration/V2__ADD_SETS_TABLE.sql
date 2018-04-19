CREATE TABLE font_sets (
  PRIMARY KEY (id),
  id serial,
  favorite_id INTEGER NOT NULL,
  set_name VARCHAR(100) NOT NULL
);