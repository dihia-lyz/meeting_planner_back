create table if not exists meeting(
    id BIGSERIAL primary key,
    meeting_type_id bigint,
    collaborators_number int,
    start_hour int,
    end_hour int,
    meeting_date date,
    foreign key(meeting_type_id) references meeting_type(id)
)