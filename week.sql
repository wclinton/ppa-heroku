-- Table: week

DROP TABLE week;

CREATE TABLE week
(
  id serial NOT NULL,
  season character varying,
  number integer,
  date date,
  CONSTRAINT week_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE week
  OWNER TO postgres;
