package com.canalPlus.meetPlanning.model;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class FixedEquipment extends Equipment{
    @Column
    private int equipmentNumber;

}
