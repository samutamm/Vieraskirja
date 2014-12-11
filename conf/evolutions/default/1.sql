# --- !Ups

CREATE TABLE viestit (
  id IDENTITY NOT NULL PRIMARY KEY,
  nimi VARCHAR(255) NOT NULL,
  sisalto VARCHAR(255) NOT NULL
);

# --- !Downs

DROP TABLE viestit;