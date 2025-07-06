package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "hotel_reviews")
@Getter
@Setter
@NoArgsConstructor
public class HotelReview {

    @Id
    @JsonIgnore
    @GeneratedValue
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    private int overallRating;
    private int cleanlinessRating;
    private int staffRating;
    private int facilitiesRating;
    private int valueRating;

    @Column(nullable = false)
    private String wouldRecommend;

    @Column(length = 1000)
    private String bestAspect;

    @Column(length = 1000)
    private String improvementSuggestion;

    @Column(length = 1000)
    private String additionalComments;

    @Column(updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
