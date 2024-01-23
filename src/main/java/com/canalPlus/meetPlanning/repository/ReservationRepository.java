package com.canalPlus.meetPlanning.repository;

import com.canalPlus.meetPlanning.dto.reservation.ReservationOutDto;
import com.canalPlus.meetPlanning.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            SELECT
                CASE
                    WHEN EXISTS (
                        SELECT r.room.id
                        FROM Reservation r
                        JOIN Meeting m
                        on m.id = r.meeting.id
                        WHERE r.room.id = :room_id
                        AND m.meetingDate =  TO_DATE(:date, 'DD/MM/YYYY')
                        And 
                        ((m.startHour <= :startHour
                        AND :startHour < (m.endHour + :interval))
                        Or(
                        :startHour = m.startHour -1
                        ))
                    ) THEN 1
                    ELSE 0
                end as result
            """)
    int isAlreadyReserved(
            @Param("room_id") Long room_id,
            @Param("date") String date,
            @Param("startHour") int startHour,
            @Param("interval") int interval);

    @Query("""
            select new com.canalPlus.meetPlanning.dto.reservation.ReservationOutDto( rm.name , m.startHour , m.endHour, m.meetingDate , m.collaboratorsNumber , rm.capacity) 
            from Reservation r
            join Meeting m
            on m.id = r.meeting.id
            join Room rm
            on rm.id = r.room.id
            """)
    List<ReservationOutDto> findAllReservations();
}
