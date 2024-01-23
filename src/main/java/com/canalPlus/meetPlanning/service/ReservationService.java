package com.canalPlus.meetPlanning.service;

import com.canalPlus.meetPlanning.dto.meeting.MeetingInDto;
import com.canalPlus.meetPlanning.dto.reservation.ReservationOutDto;
import com.canalPlus.meetPlanning.model.*;
import com.canalPlus.meetPlanning.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.canalPlus.meetPlanning.utils.Constants.*;

@Slf4j
@Service
public class ReservationService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    private final MeetingTypeRepository meetingTypeRepository;

    private final MeetingRepository meetingRepository;

    private final RemovableEquipmentRepository removableEquipmentRepository;

    private final EquipmentRepository equipmentRepository;

    private final EntityManager entityManager;

    public ReservationService(RoomRepository roomRepository, ReservationRepository reservationRepository, MeetingTypeRepository meetingTypeRepository, MeetingRepository meetingRepository, RemovableEquipmentRepository removableEquipmentRepository, EquipmentRepository equipmentRepository, EntityManager entityManager) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.meetingTypeRepository = meetingTypeRepository;
        this.meetingRepository = meetingRepository;
        this.removableEquipmentRepository = removableEquipmentRepository;
        this.equipmentRepository = equipmentRepository;
        this.entityManager = entityManager;
    }

    public List<ReservationOutDto> getAll() {
        return reservationRepository.findAllReservations();
    }

    @Transactional
    public Room reserve(MeetingInDto meetingInDto) throws Exception {

        List<Room> rooms = roomRepository.findAll();
        List<Room> sortedRoomsByPlacesNumber;
        List<Room> notReservedRooms;

        log.info("all rooms {}", rooms);
        if (!areValideMeetingHours(meetingInDto)) {
            throw new Exception("Les horaires de votre reunions ne sont pas valides");
        }

        //if SRMeeting has under 3 collaborators then don't reserve any room
        if (meetingInDto.getMeetingTypeId() == 3 && !isValideRSMeeting(meetingInDto)) {
            throw new Exception("votre reunion ne necessite pas une salle");
        }

        List<Equipment> requiredEquipment = meetingTypeRepository.findEquipmentsById(meetingInDto.getMeetingTypeId());

        sortedRoomsByPlacesNumber = filterAndSortRoomsByPlacesNumber(rooms, meetingInDto.getCollaboratorsNumber(), requiredEquipment);

        if (sortedRoomsByPlacesNumber.isEmpty()) {
            throw new Exception("Aucune salle peut supporter le nombre des participants a votre reunion! Veuillez choisir un autre horaire/jour");
        }

        notReservedRooms = getNotReservedRooms(sortedRoomsByPlacesNumber, meetingInDto.getMeetingDate(), Integer.parseInt(meetingInDto.getStartHour()));

        if (notReservedRooms.isEmpty()) {
            throw new Exception("Toutes les salles sont reservees pour cet horaire !");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Meeting meeting = Meeting.builder()
                .meetingDate(LocalDate.parse(meetingInDto.getMeetingDate(), formatter))
                .meetingType(meetingTypeRepository.findById(meetingInDto.getMeetingTypeId()).get())
                .startHour(Integer.parseInt(meetingInDto.getStartHour()))
                .endHour(Integer.parseInt(meetingInDto.getEndHour()))
                .collaboratorsNumber(meetingInDto.getCollaboratorsNumber())
                .build();


        // if no equipment is required then reserve first room(small one)
        if (requiredEquipment.isEmpty()) {
            Meeting meeting1 = meetingRepository.save(meeting);

            Reservation reservation = Reservation.builder().meeting(meeting1).room(notReservedRooms.get(0)).build();
            reservationRepository.save(reservation);

            return notReservedRooms.get(0);
        }

        //if there is required equipments
        for (Room r : notReservedRooms) {
            boolean hasAllEquipments = r.getEquipments().size() == requiredEquipment.size();

            //if room has all the required equipments then reserve it
            if (hasAllEquipments) {
                Room roomtoReserve = Room.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .capacity(r.getCapacity())
                        .equipments(r.getEquipments())
                        .build();
                Meeting meeting1 = meetingRepository.save(meeting);

                Reservation reservation = Reservation.builder().meeting(meeting1).room(notReservedRooms.get(0)).build();
                reservationRepository.save(reservation);
                return roomtoReserve;
            } else {

//                if room has missed equipments then reserve the quipment
//                get missed equipments
                List<Equipment> missedElements = requiredEquipment.stream()
                        .map(equipment -> Equipment.builder().id(equipment.getId()).name(equipment.getName()).build())
                        .filter(element -> !r.getEquipments()
                                .stream()
                                .map(equipment -> Equipment.builder().id(equipment.getId()).name(equipment.getName()).build())
                                .collect(Collectors.toList())
                                .contains(element))
                        .map(equipment -> Equipment.builder().id(equipment.getId()).name(equipment.getName()).build())
                        .collect(Collectors.toList());

                //if equipments are available (not reserved) then reserve them
                if (equipmentsAreAvailable(missedElements, meetingInDto.getMeetingDate(), Integer.parseInt(meetingInDto.getStartHour()))) {
                    Room roomtoReserve = Room.builder()
                            .id(r.getId())
                            .name(r.getName())
                            .capacity(r.getCapacity())
                            .equipments(r.getEquipments())
                            .build();

                    List<RemovableEquipment> reservedEquipment = new ArrayList<>();
                    missedElements.forEach(equipment ->
                    {
                        Optional<RemovableEquipment> rem = removableEquipmentRepository.findById(equipment.getId());
                        rem.ifPresent(reservedEquipment::add);
                    });


                    Meeting meeting1 = meetingRepository.save(meeting);
                    Reservation reservation = Reservation.builder()
                            .meeting(meeting1)
                            .room(notReservedRooms.get(0))
                            .equipments(reservedEquipment)
                            .build();

                    reservationRepository.save(reservation);
                    return roomtoReserve;
                }
            }
        }
        //No room was reserved
        throw new Exception("Les equipements requis par votre reunions sont deja reserves");
    }


    private boolean areValideMeetingHours(MeetingInDto meetingInDto) {
        return Integer.parseInt(meetingInDto.getStartHour()) >= START_WORK_HOUR && Integer.parseInt(meetingInDto.getEndHour()) <= END_WORK_HOUR;
    }

    private boolean isValideRSMeeting(MeetingInDto meetingInDto) throws Exception {
        return meetingInDto.getCollaboratorsNumber() > meetingTypeRepository.findById(3L)
                .orElseThrow(() -> new Exception("Erreur! Type de meeting invalide"))
                .getMinCollaboratorsNumber();
    }

    private List<Room> filterAndSortRoomsByPlacesNumber(List<Room> rooms, int requiredNumber, List<
            Equipment> requiredEquipment) {
        log.info("Start filtering rooms by places number and equipments");

        return rooms.stream().filter(s -> requiredNumber <= s.getCapacity() * CAPACITY_PERCENT)
                .sorted(Comparator.comparingDouble(s -> {
                            double capacityDiff = Math.abs(s.getCapacity() * CAPACITY_PERCENT - requiredNumber);
                            //get room with minimum equipments
                            if (requiredEquipment.isEmpty()) {
                                int nbrEquipment = s.getEquipments().size();
                                return capacityDiff + nbrEquipment * 0.001;

                            } else {
                                entityManager.clear();
                                //get the room with maximum of commun equipments with the required ones
                                List<Equipment> required = requiredEquipment.stream()
                                        .map(equipment -> Equipment.builder().name(equipment.getName()).id(equipment.getId()).build())
                                        .collect(Collectors.toList());

                                List<Equipment> equipments = s.getEquipments().stream()
                                        .map(equip -> Equipment.builder().name(equip.getName()).id(equip.getId()).build())
                                        .collect(Collectors.toList());

                                long commonElements = equipments.stream()
                                        .filter(required::contains)
                                        .toList().size();

                                return capacityDiff - commonElements * 0.001;
                            }
                        }
                ))
                .collect(Collectors.toList());
    }

    private List<Room> getNotReservedRooms(List<Room> sortedRoomsByPlacesNumber, String date, int startHour) {
        log.info("Start getting all rooms not already reserved");
        List<Room> notReservedRooms = new ArrayList<>();

        sortedRoomsByPlacesNumber.forEach(s -> {
            Room room = s;
            if (reservationRepository.isAlreadyReserved(room.getId(), date, startHour, INTERVAL_HOURS_NUMBER) == 0) {
                notReservedRooms.add(room);
            }
        });
        return notReservedRooms;
    }

    Boolean equipmentsAreAvailable(List<Equipment> missedElements, String date, int startHour) throws Exception {
        log.info("start checking if equipments are available to reserve");
        boolean areAvailable = true;
        for (Equipment equipment : missedElements) {
            entityManager.clear();
            Optional<RemovableEquipment> removableEquipment = removableEquipmentRepository.findById(equipment.getId());
//            log.info("removable equip {}", removableEquipment);
            // pas d'equipement amovible pour ce materiel manquant
            // ou cet equipement est reservee
            if (removableEquipment.isEmpty()
                    || removableEquipmentRepository.equipmentAlreadyReserved(removableEquipment.get().getId(), removableEquipment.get().getEquipmentNumber(), date, startHour) == 1) {
                areAvailable = false;
                break;
            }
        }
        return areAvailable;
    }
}