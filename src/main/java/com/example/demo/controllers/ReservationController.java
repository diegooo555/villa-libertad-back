package com.example.demo.controllers;

import java.util.List;
import java.util.UUID;

import com.example.demo.dtos.ReservationDto;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
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
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveReservation(@RequestBody ReservationDto reservationDto, @RequestHeader(name = "Authorization") String authorizationHeader)  {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authorizationHeader.substring(7);
        Claims claims = JwtUtil.getClaimsFromToken(token);
        String email = claims.getSubject();
        reservationDto.setEmail(email);
        Reservation savedReservation = reservationService.saveReservation(reservationDto);
        return  savedReservation != null ? ResponseEntity.ok(savedReservation) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Reservation>> getAllReservations(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        List<Reservation> reservations = reservationService.findReservationsByUserId(user.getId());
        return ResponseEntity.ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}
