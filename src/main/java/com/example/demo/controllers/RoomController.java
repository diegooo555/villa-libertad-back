package com.example.demo.controllers;

import com.example.demo.dtos.RoomDto;
import com.example.demo.entities.Room;
import com.example.demo.services.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/available")
    public ResponseEntity<?> searchAvailableRooms(
            @RequestParam("checkIn") String checkIn,
            @RequestParam("checkOut") String checkOut,
            @RequestParam("guests") int guests) {
        try {
            LocalDate startDate = LocalDate.parse(checkIn);
            LocalDate endDate = LocalDate.parse(checkOut);
            List<Room> availableRooms = roomService.findAllAvailableRooms(startDate, endDate, guests);

            if (availableRooms.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }

            return ResponseEntity.ok(availableRooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al buscar habitaciones disponibles: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveRoom(RoomDto roomDto){
        try {
            Room room = roomService.saveRoom(roomDto);

            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la habitacion: " + e.getMessage());
        }
    }
}