package com.canalPlus.meetPlanning.reservation;

import com.canalPlus.meetPlanning.dto.meeting.MeetingInDto;
import com.canalPlus.meetPlanning.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=7000"})
@ActiveProfiles("test")
@Slf4j
public class ReservationTest {

    @LocalServerPort
    private static int PORT = 7000;

    private final TestRestTemplate restTemplate;
    private static String apiUrl;
    @Autowired
    private final Flyway flyway;

    @Autowired
    public ReservationTest(TestRestTemplate restTemplate, Flyway flyway) {
        this.restTemplate = restTemplate;
        this.flyway = flyway;
    }

    @BeforeAll
    public void clearDatabase() {
        flyway.clean();
        flyway.migrate();
    }

    @BeforeAll
    public static void initApiUrl() {
        apiUrl = "http://localhost:" + PORT + "/api/v1/reservation";
    }

    @Test
    void itShouldCreateReservationWithRoomE3001() {
        //GIVEN
        MeetingInDto meetingInDto = MeetingInDto.builder().meetingTypeId(1L).meetingDate("08/01/2024").collaboratorsNumber(8).startHour("9").endHour("10").build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MeetingInDto> requestEntity = new HttpEntity<>(meetingInDto, headers);

        //WHEN
        ResponseEntity<Room> response = restTemplate.postForEntity(apiUrl, requestEntity, Room.class);

        //THEN
        assertEquals("E3001", response.getBody().getName());
        assertEquals(13, response.getBody().getCapacity());
    }


    @Test
    void itShouldCreateReservationWithRoomE3003() {

        MeetingInDto meetingInDto = MeetingInDto.builder().meetingTypeId(1L).meetingDate("08/01/2024").collaboratorsNumber(6).startHour("9").endHour("10").build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MeetingInDto> request = new HttpEntity<>(meetingInDto, headers);

        ResponseEntity<Room> response = restTemplate.postForEntity(apiUrl, request, Room.class);

        assertEquals("E3003", response.getBody().getName());
        assertEquals(9, response.getBody().getCapacity());
    }

    @Test
    void itShouldCreateReservationWithRoomE2003() {

        MeetingInDto meetingInDto = MeetingInDto.builder().meetingTypeId(1L).meetingDate("08/01/2024").collaboratorsNumber(4).startHour("11").endHour("12").build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MeetingInDto> request = new HttpEntity<>(meetingInDto, headers);

        ResponseEntity<Room> response = restTemplate.postForEntity(apiUrl, request, Room.class);

        assertEquals("E2003", response.getBody().getName());
        assertEquals(7, response.getBody().getCapacity());
    }

//    @Test
//    void itShouldNotCreateReservationWithRsMeetingAndTwoCollaborators() {
//
//        MeetingInDto meetingInDto = MeetingInDto.builder().meetingTypeId(3L).meetingDate("08/01/2024").collaboratorsNumber(2).startHour("11").endHour("12").build();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<MeetingInDto> request = new HttpEntity<>(meetingInDto, headers);
//
//        ResponseEntity<Object> response = restTemplate.postForEntity(apiUrl, request, Object.class);
//        log.info("response {}", response);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//    }

    @Test
    void itShouldCreateReservationAt11HWithRoomE3001() {

        MeetingInDto meetingInDto = MeetingInDto.builder().meetingTypeId(2L).meetingDate("08/01/2024").collaboratorsNumber(9).startHour("11").endHour("12").build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MeetingInDto> request = new HttpEntity<>(meetingInDto, headers);

        ResponseEntity<Room> response = restTemplate.postForEntity(apiUrl, request, Room.class);

        assertEquals("E3001", response.getBody().getName());
    }
}
