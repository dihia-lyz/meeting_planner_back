package com.canalPlus.meetPlanning.dto.meeting;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingInDto {

    private Long meetingTypeId;
    private int collaboratorsNumber;
    private String meetingDate;
    private String startHour;
    private String endHour;
}
