package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class ReservationDto {
    private String email;
    private UUID hotelId;
    private UUID roomId;
    private Long numPersons;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String reference;
    private BigDecimal total;
    private BigDecimal deposit;
}
