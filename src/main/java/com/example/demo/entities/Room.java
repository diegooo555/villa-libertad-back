package com.example.demo.entities;

import com.example.demo.dtos.RoomDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "rooms")
@NoArgsConstructor
public class Room {

    @Id
    @JsonIgnore
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

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

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> images = new ArrayList<>();

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
