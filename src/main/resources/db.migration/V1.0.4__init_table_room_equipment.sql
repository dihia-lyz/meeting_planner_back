create table if not exists room_equipment (
	room_id bigint,
	equipment_id bigint ,
	foreign key (room_id ) references room(id),
	foreign key (equipment_id ) references fixed_equipment(id),
	primary key(room_id,equipment_id)
);

insert INTO room_equipment (room_id, equipment_id) VALUES
    (2,1),
    (3,2),
    (4,3),
    (6,1),
    (6,4),
    (8,3),
    (9,1),
    (9,2),
    (9,4),
    (11,1),
    (11,2);

