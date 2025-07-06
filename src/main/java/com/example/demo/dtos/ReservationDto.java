package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ReservationDto {
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @NotBlank(message = "Hotel Id cannot be empty")
    private UUID hotelId;
    @NotBlank(message = "Room Id cannot be empty")
    private UUID roomId;
    @NotBlank(message = "Nun Persons cannot be empty")
    private Long numPersons;
    @NotBlank(message = "Check in cannot be empty")
    private LocalDate checkIn;
    @NotBlank(message = "Check out cannot be empty")
    private LocalDate checkOut;
    @NotBlank(message = "Reference cannot be empty")
    private String reference;
    @NotBlank(message = "Total cannot be empty")
    private BigDecimal total;
    @NotBlank(message = "Deposit cannot be empty")
    private BigDecimal deposit;
}
