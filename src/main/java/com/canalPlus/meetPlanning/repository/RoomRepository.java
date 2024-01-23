package com.canalPlus.meetPlanning.repository;

import com.canalPlus.meetPlanning.model.FixedEquipment;
import com.canalPlus.meetPlanning.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
            select r.equipments from  Room r
            where r.id = :id
            """)
    List<FixedEquipment> findEquipments(Long id);
}
