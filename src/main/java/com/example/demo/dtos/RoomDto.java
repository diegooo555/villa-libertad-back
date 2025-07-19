package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoomDto {
    @NotBlank
    private UUID hotelId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Type cannot be empty")
    private String type;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotNull
    private BigDecimal price;
    @NotBlank(message = "Url image cannot be empty")
    private String urlImg;
    @Size(min = 1, message = "Numbers list cannot be empty")
    private List<String> images;
    @NotNull
    private Integer capacity;
}
