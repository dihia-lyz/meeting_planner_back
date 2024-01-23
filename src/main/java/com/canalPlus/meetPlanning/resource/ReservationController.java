package com.canalPlus.meetPlanning.resource;

import com.canalPlus.meetPlanning.dto.meeting.MeetingInDto;
import com.canalPlus.meetPlanning.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/reservation")
public class ReservationController {

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestBody MeetingInDto meetingInDto) {
        try {
            return new ResponseEntity<>(reservationService.reserve(meetingInDto), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(reservationService.getAll(), HttpStatus.OK);
    }

}
