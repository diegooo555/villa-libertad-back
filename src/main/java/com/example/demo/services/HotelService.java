package com.example.demo.services;

import com.example.demo.dtos.HotelDto;
import com.example.demo.entities.Hotel;
import com.example.demo.exepcions.ResourceNotFoundException;
import com.example.demo.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Hotel> findAllHotels() {
        List<Hotel> hotels =  hotelRepository.findAll();
        if(hotels.isEmpty()){
            throw new ResourceNotFoundException("Haven't created hotels yet");
        }

        return hotels;
    }
}
