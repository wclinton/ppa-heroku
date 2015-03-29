DROP TABLE match;

CREATE TABLE match
(
  id serial NOT NULL,
  weekId serial NOT NULL,
  homeTeam integer,
  awayTeam integer,
  table1 character varying,
  match integer,
  table2 character varying,
 
  CONSTRAINT match_pkey PRIMARY KEY (id)

)
WITH (
  OIDS=FALSE
);
ALTER TABLE match
  OWNER TO postgres;