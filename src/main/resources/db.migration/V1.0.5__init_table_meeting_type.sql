create table meeting_type(
	id BIGSERIAL primary key,
	name varchar ,
	min_collaborators_number int
);

INSERT INTO meeting_type (id,name,min_collaborators_number) VALUES
	 (1,'Visioconférence (VC) ',0),
	 (2,'Séances de partage et d''études de cas (SPEC)',0),
	 (3,'Réunion simple (RS)',3),
	 (4,'Réunion couplée (RC)',0);