create table if not exists reservation(
    id BIGSERIAL primary key ,
     room_id bigint,
     meeting_id bigint,
     foreign key (room_id) references room(id),
     foreign key (meeting_id) references meeting(id)
)