package com.example.demo.services;

import com.example.demo.entities.HotelReview;
import com.example.demo.repositories.HotelReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelReviewService {

    private final HotelReviewRepository hotelReviewRepository;

    public HotelReviewService(HotelReviewRepository hotelReviewRepository) {
        this.hotelReviewRepository = hotelReviewRepository;
    }

    public HotelReview save(HotelReview hotelReview) {
        return hotelReviewRepository.save(hotelReview);
    }
}
