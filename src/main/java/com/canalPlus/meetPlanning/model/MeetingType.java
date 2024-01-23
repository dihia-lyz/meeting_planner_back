package com.canalPlus.meetPlanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.ToString.Exclude;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class MeetingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @Column
    private int minCollaboratorsNumber;

    @OneToMany(mappedBy = "meetingType")
    @JsonIgnore
    List<Meeting> meetings;

    @ManyToMany
    @JoinTable(
            name = "meeting_type_equipment",
            joinColumns = @JoinColumn(name = "meeting_type_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    @JsonIgnore
    List<Equipment> equipments;

    @Override
    public String toString() {
        return "EntityA{id=" + id + ", name=" + name + "minCollaboratorsNumber=" + minCollaboratorsNumber + "}";
    }
}
