DROP SCHEMA PUBLIC CASCADE;
/*http://hsqldb.org/doc/guide/compatibility-chapt.html#coc_compatibility_mysql*/

CREATE TABLE court (
  court_id 						  INTEGER 					  NOT NULL,
  court_name				    VARCHAR(100),
  phone					        VARCHAR(50),
  extension				      VARCHAR(15),
  website					      VARCHAR(200),
 	payment_system			  VARCHAR(50),
  address 				      VARCHAR(50),
  city 					        VARCHAR(50),
  state 					      VARCHAR(25),
  zip_code 				      VARCHAR(12),
  latitude 				      DOUBLE PRECISION,
  longitude 				    DOUBLE PRECISION,
  citation_expires_after_days	SMALLINT,
  rights_type				  VARCHAR(25),
  rights_value				  VARCHAR(250)
);

CREATE TABLE judges (
  id 						INTEGER 					NOT NULL,
  judge		 			VARCHAR(100),
	court_id			INTEGER						NOT NULL
);