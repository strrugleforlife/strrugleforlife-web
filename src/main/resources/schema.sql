CREATE TABLE IF NOT EXISTS "item" (
  id serial CONSTRAINT firstkey PRIMARY KEY,
  name varchar(255),
  price integer,
  vendor varchar(255)
);