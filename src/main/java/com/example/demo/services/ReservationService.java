package com.example.demo.services;

import java.util.List;

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
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setHotel(hotel);
        reservation.setRoom(room);
        reservation.setNumPersons(reservationDto.getNumPersons());
        reservation.setStatus("CONFIRMING");
        reservation.setCheckIn(reservationDto.getCheckIn());
        reservation.setCheckOut(reservationDto.getCheckOut());
        reservation.setReference(reservationDto.getReference());
        reservation.setTotal(reservationDto.getTotal());
        reservation.setDeposit(reservationDto.getDeposit());
        reservationRepository.save(reservation);
        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> findReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }
}
