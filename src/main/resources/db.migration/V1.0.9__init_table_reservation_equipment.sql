create table if not exists equipment_reservation(
    equipment_id bigint,
    reservation_id bigint,
    foreign key (equipment_id) references removable_equipment(id),
    foreign key(reservation_id) references reservation(id),
    primary key(equipment_id, reservation_id)
)