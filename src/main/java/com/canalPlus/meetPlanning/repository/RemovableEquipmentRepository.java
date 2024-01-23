package com.canalPlus.meetPlanning.repository;

import com.canalPlus.meetPlanning.model.RemovableEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RemovableEquipmentRepository extends JpaRepository<RemovableEquipment, Long> {

    @Query("""
               SELECT
                   CASE
                       WHEN (
                           (SELECT COUNT(er)
                            FROM RemovableEquipment er
                                JOIN er.reservations r
                                JOIN r.meeting m
                           WHERE er.id = :idEquipment
                              AND m.meetingDate = TO_DATE(:date, 'DD/MM/YYYY')
                              AND m.startHour = :startHour
                           ) = :equipmentNumber
                       ) THEN 1
                       ELSE 0
                   END
            """)
    int equipmentAlreadyReserved(
            @Param("idEquipment") Long id_equipment,
            @Param("equipmentNumber") int equipmentNumber,
            @Param("date") String date,
            @Param("startHour") int startHour
    );
}
