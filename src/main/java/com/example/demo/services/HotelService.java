package com.example.demo.services;

import com.example.demo.dtos.HotelDto;
import com.example.demo.entities.Hotel;
import com.example.demo.repositories.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public Hotel saveHotel(HotelDto hotelDto){
        Hotel hotel = new Hotel(hotelDto);
        return hotelRepository.save(hotel);
    }

    public Iterable<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }
}
