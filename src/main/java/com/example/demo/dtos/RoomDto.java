package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RoomDto {

    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Type cannot be empty")
    private String type;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "Price cannot be empty")
    private BigDecimal price;
    @NotBlank(message = "Url image cannot be empty")
    private String urlImg;
    @NotBlank(message = "Images cannot be empty")
    private List<String> images;
    @NotBlank(message = "Capacity cannot be empty")
    private Integer capacity;
}
