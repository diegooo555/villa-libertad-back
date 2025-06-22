package com.example.demo.services;

import com.example.demo.dtos.RoomDto;
import com.example.demo.entities.Room;
import com.example.demo.repositories.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room saveRoom(RoomDto roomDto) {
        Room room = new Room(roomDto);
        return roomRepository.save(room);
    }

    public List<Room> findAllRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    public List<Room> findAllAvailableRooms(LocalDate startDate, LocalDate endDate, int guests){
        return roomRepository.findAvailableRooms(startDate, endDate, guests);
    }
}
