create TABLE if NOT EXISTS room (
    id BIGSERIAL PRIMARY KEY,
    name varchar,
    capacity int
);

insert into room (id,capacity,name) values
	 (1,23,'E1001'),
     (2,10,'E1002'),
     (3,8,'E1003'),
     (4,4,'E1004'),
     (5,4,'E2001'),
     (6,15,'E2002'),
     (7,7,'E2003'),
     (8,9,'E2004'),
     (9,13,'E3001'),
     (10,8,'E3002'),
     (11,9,'E3003'),
     (12,4,'E3004');