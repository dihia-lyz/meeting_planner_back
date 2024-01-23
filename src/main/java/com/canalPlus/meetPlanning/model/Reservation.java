package com.canalPlus.meetPlanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "equipment_reservation",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id", referencedColumnName="id"))
    private List<RemovableEquipment> equipments;

    @Override
    public String toString() {
        return " " + this.id + "" + this.room +""+this.equipments;
    }


}
