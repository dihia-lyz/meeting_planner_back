create table if not exists fixed_equipment(
    id BIGSERIAL primary key,
    equipment_number int,
    foreign key(id) references equipment(id)
);

INSERT INTO fixed_equipment (id, equipment_number) VALUES
	 (1,4),
	 (2,3),
	 (3,2),
	 (4,2)