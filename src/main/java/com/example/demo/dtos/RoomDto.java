package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RoomDto {
    private String name;
    private String type;
    private String description;
    private BigDecimal price;
    private String urlImg;
    private List<String> images;
    private Integer capacity;
}
