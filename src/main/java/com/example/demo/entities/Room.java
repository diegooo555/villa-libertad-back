package com.example.demo.entities;

import com.example.demo.dtos.RoomDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "rooms")
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "url_img", nullable = false)
    private String urlImg;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Boolean deleted = false;

    public Room (RoomDto roomDto){
        name = roomDto.getName();
        type = roomDto.getType();
        description = roomDto.getDescription();
        price = roomDto.getPrice();
        urlImg = roomDto.getUrlImg();
        capacity = roomDto.getCapacity();
    }
}
