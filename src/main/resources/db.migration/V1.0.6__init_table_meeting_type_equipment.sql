create table if not exists meeting_type_equipment(
    meeting_type_id bigint,
    equipment_id bigint,
    foreign key (meeting_type_id) references meeting_type(id),
    foreign key (equipment_id) references equipment(id),
    primary key(meeting_type_id, equipment_id)
);

INSERT INTO meeting_type_equipment (meeting_type_id,equipment_id) VALUES
	 (1,1),
	 (1,2),
	 (1,4),
	 (2,3),
	 (4,1),
	 (4,2),
	 (4,3);