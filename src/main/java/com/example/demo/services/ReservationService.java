package com.example.demo.services;

import java.util.List;
import java.util.UUID;

import com.example.demo.dtos.ReservationDto;
import com.example.demo.entities.Hotel;
import com.example.demo.entities.Room;
import com.example.demo.entities.User;
import com.example.demo.repositories.HotelRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Reservation;
import com.example.demo.repositories.ReservationRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation saveReservation(ReservationDto reservationDto) {
        User user = userRepository.findByEmail(reservationDto.getEmail()).orElseThrow();
        Hotel hotel = hotelRepository.findById(reservationDto.getHotelId()).orElseThrow();
        Room room = roomRepository.findById(reservationDto.getRoomId()).orElseThrow();

        Reservation reservation = reservationRepository.findByReference(reservationDto.getReference());
        if(reservation != null) {
            return reservation;
        }
        Reservation newReservation = new Reservation();
        newReservation.setUser(user);
        newReservation.setHotel(hotel);
        newReservation.setRoom(room);
        newReservation.setNumPersons(reservationDto.getNumPersons());
        newReservation.setCheckIn(reservationDto.getCheckIn());
        newReservation.setCheckOut(reservationDto.getCheckOut());
        newReservation.setReference(reservationDto.getReference());
        newReservation.setTotal(reservationDto.getTotal());
        newReservation.setDeposit(reservationDto.getDeposit());
        reservationRepository.save(newReservation);
        return newReservation;
    }

    public void deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findReservationsByUserId(UUID userId) {
        return reservationRepository.findByUserIdAndStatus(userId, "UNCONFIRMED");
    }
}
