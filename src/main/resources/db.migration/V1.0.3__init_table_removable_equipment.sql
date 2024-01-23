create table if not exists removable_equipment(
    id BIGSERIAL primary key,
    equipment_number int,
    reserved_number int DEFAULT 0,
    foreign key (id) references equipment(id)
);

INSERT INTO removable_equipment (id,equipment_number) VALUES
	 (1,5),
	 (2,4),
	 (3,2),
	 (4,4)