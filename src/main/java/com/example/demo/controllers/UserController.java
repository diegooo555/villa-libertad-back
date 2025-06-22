package com.example.demo.controllers;

import com.example.demo.entities.Role;
import io.jsonwebtoken.Claims;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dtos.UserDto;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtil;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('VISITOR')")
    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(
            @RequestBody UserDto userDto,
            @RequestHeader(name = "Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authorizationHeader.substring(7);
        Claims claims = JwtUtil.getClaimsFromToken(token);
        String email = claims.getSubject();

        User existingUser = userService.findUserByEmail(email);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya está registrado.");
        }

        userDto.setEmail(email);
        User savedUser = userService.saveUser(userDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedUser); // 201
    }

    @PostMapping("/api/token/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(value = "smartorlsasvcderyocxakfgh", required = false) String refreshToken) {
        System.out.println("ESTA VUELTA ESTA FUNCIONANDO BIEN CHIMBA");
        if (refreshToken == null || !JwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Claims claims = JwtUtil.getClaimsFromToken(refreshToken);
        String email = claims.getSubject();
        String givenName = claims.get("given_name").toString();
        String picture = claims.get("picture").toString();
        Set<Role> roles = userService.findRolesByUserEmail(email);
        String newAccessToken = JwtUtil.generateAccessToken(givenName, picture, email, roles);
        return ResponseEntity.ok(Map.of("token", newAccessToken));
    }
}