package com.canalPlus.meetPlanning.repository;

import com.canalPlus.meetPlanning.model.Equipment;
import com.canalPlus.meetPlanning.model.MeetingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long> {

    @Query("SELECT mt.equipments FROM MeetingType mt WHERE mt.id = :id")
    List<Equipment> findEquipmentsById(@Param("id") Long id);
}
