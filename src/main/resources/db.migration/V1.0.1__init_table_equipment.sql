CREATE TABLE if NOT EXISTS equipment(
 id BIGSERIAL PRIMARY KEY,
 name varchar
);
INSERT INTO equipment (id,name) VALUES
	 (1,'écran'),
	 (2,'pieuvre'),
	 (3,'tableau'),
	 (4,'webcam')