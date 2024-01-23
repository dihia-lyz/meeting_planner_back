package com.canalPlus.meetPlanning.resource;

import com.canalPlus.meetPlanning.service.MeetingTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/meetingType")
public class MeetingTypeController {

    private final MeetingTypeService meetingTypeService;

    public MeetingTypeController(MeetingTypeService meetingTypeService) {
        this.meetingTypeService = meetingTypeService;
    }

    @GetMapping
    public ResponseEntity<?> getMeetingTypes(){
        return new ResponseEntity<>(meetingTypeService.getMeetingTypes(), HttpStatus.OK);
    }
}
