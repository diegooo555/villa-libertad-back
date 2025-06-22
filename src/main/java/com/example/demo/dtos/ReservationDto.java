package com.example.demo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ReservationDto {
    private String email;
    private Long hotelId;
    private Long roomId;
    private Long numPersons;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String reference;
    private BigDecimal total;
    private BigDecimal deposit;
}
