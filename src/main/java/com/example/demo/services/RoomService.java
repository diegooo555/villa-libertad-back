package com.example.demo.services;

import com.example.demo.dtos.RoomDto;
import com.example.demo.entities.Hotel;
import com.example.demo.entities.Room;
import com.example.demo.exepcions.BadRequestException;
import com.example.demo.exepcions.ResourceNotFoundException;
import com.example.demo.repositories.HotelRepository;
import com.example.demo.repositories.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public Room saveRoom(RoomDto roomDto) {
        if(roomDto.getPrice().compareTo(new BigDecimal("50000.00")) < 0) {
            throw new BadRequestException("Price must be greater than or equal to 50000");
        }
        Hotel hotel = hotelRepository.findById(roomDto.getHotelId()).orElseThrow( () -> new ResourceNotFoundException("Hotel"));
        Room room = new Room(roomDto);
        room.setHotel(hotel);
        return roomRepository.save(room);
    }

    public List<Room> findAllRoomsByHotelId(UUID hotelId) {
        List<Room> rooms =  roomRepository.findByHotelId(hotelId);
        if (rooms.isEmpty()) {
            throw new ResourceNotFoundException("No rooms available for hotel with ID: " + hotelId);
        }

        return rooms;
    }

    public List<Room> findAllAvailableRooms(LocalDate startDate, LocalDate endDate, int guests){
        return roomRepository.findAvailableRooms(startDate, endDate, guests);
    }
}
