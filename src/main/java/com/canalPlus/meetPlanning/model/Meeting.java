package com.canalPlus.meetPlanning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate meetingDate;

    @Column
    private int startHour;

    @Column
    private int endHour ;

    @Column
    private int collaboratorsNumber;

    @ManyToOne
    @JoinColumn(name = "meeting_type_id")
    private MeetingType meetingType;

}
