package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.DecimalMin;

@Getter
@Setter
public class ReservationDto {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Hotel Id cannot be null")
    private UUID hotelId;

    @NotNull(message = "Room Id cannot be null")
    private UUID roomId;

    @NotNull(message = "Number of persons cannot be null")
    @Min(value = 1, message = "Number of persons must be at least 1")
    private Long numPersons;

    @NotNull(message = "Check-in date cannot be null")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkIn;

    @NotNull(message = "Check-out date cannot be null")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOut;

    @NotBlank(message = "Reference cannot be empty")
    private String reference;

    @NotNull(message = "Total cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than 0")
    private BigDecimal total;

    @NotNull(message = "Deposit cannot be null")
    @DecimalMin(value = "0.0", message = "Deposit must be zero or greater")
    private BigDecimal deposit;
}