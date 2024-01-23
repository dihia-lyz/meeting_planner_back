package com.canalPlus.meetPlanning.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private int capacity;

    @ManyToMany
//    @ToString.Exclude
//    @JsonIgnore
    @JoinTable(
            name = "room_equipment",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    List<FixedEquipment> equipments;

    public List<FixedEquipment> getEquipments() {
        return this.equipments;
    }

}
