package com.example.demo.entities;

import com.example.demo.dtos.HotelDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "hotels")
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(name = "url_img", nullable = false, unique = true)
    private String urlImg;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Hotel(HotelDto hotelDto) {
        name = hotelDto.getName();
        address = hotelDto.getAddress();
        urlImg = hotelDto.getUrlImg();
        description = hotelDto.getDescription();
    }
}
