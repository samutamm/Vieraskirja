
# --- !Ups
ALTER TABLE viestit ADD ref VARCHAR(255) NOT NULL DEFAULT 'not referenced';


# --- !Downs
ALTER TABLE viestit DROP COLUMN ref;