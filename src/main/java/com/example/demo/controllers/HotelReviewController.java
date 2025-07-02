package com.example.demo.controllers;

import com.example.demo.entities.HotelReview;
import com.example.demo.services.HotelReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class HotelReviewController {

    private final HotelReviewService hotelReviewService;

    public HotelReviewController(HotelReviewService hotelReviewService) {
        this.hotelReviewService = hotelReviewService;
    }

    @PostMapping("/save")
    public ResponseEntity<HotelReview> save(@RequestBody HotelReview hotelReview) {
        HotelReview savedHotelReview = hotelReviewService.save(hotelReview);
        return ResponseEntity.ok(savedHotelReview);
    }
}
