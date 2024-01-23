package com.canalPlus.meetPlanning.service;

import com.canalPlus.meetPlanning.model.MeetingType;
import com.canalPlus.meetPlanning.repository.MeetingTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingTypeService {

    private final MeetingTypeRepository meetingTypeRepository;

    public MeetingTypeService(MeetingTypeRepository meetingTypeRepository) {
        this.meetingTypeRepository = meetingTypeRepository;
    }

    public List<MeetingType> getMeetingTypes(){
        return meetingTypeRepository.findAll();
    }
}
