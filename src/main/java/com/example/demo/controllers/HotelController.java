package com.example.demo.controllers;

import com.example.demo.dtos.HotelDto;
import com.example.demo.entities.Hotel;
import com.example.demo.services.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<Iterable<Hotel>> getAllHotels() {
        Iterable<Hotel> hotels = hotelService.findAllHotels();
        return hotels != null ? ResponseEntity.ok(hotels) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/saveHotel")
    public ResponseEntity<HotelDto> saveHotel(HotelDto hotelDto) {
        Hotel hotel = hotelService.saveHotel(hotelDto);
        return hotel != null ? ResponseEntity.ok(hotelDto) : ResponseEntity.badRequest().build();
    }
}
