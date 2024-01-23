package com.canalPlus.meetPlanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class RemovableEquipment extends Equipment {
    @Column
    private int equipmentNumber;

    @ManyToMany(mappedBy = "equipments")
    @JsonIgnore
    private List<Reservation> reservations;

    @Override
    public String toString(){
        return "RemovableEquipment : "+this.getId()+"  equipmentNumber: "+ this.equipmentNumber;
    }

    public List<Reservation> getReservations(){
        return this.reservations;
    }
}
