package com.example.demo.controllers;

import java.util.List;

import com.example.demo.dtos.ReservationDto;
import com.example.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.Reservation;
import com.example.demo.services.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/save")
    public ResponseEntity<Reservation> saveReservation(@RequestBody ReservationDto reservationDto, @RequestHeader(name = "Authorization") String authorizationHeader)  {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        Claims claims = JwtUtil.getClaimsFromToken(token);
        String email = claims.getSubject();
        reservationDto.setEmail(email);
        Reservation savedReservation = reservationService.saveReservation(reservationDto);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable Long userId) {
        System.out.println("userId: " + userId);
        System.out.println("HOllaaaaaa");
        List<Reservation> reservations = reservationService.findReservationsByUserId(userId);
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
