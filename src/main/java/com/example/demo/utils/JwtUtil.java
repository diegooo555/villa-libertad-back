package com.example.demo.utils;

import com.example.demo.entities.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.security.oauth2.jwt.JwtException;

public class JwtUtil {

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15 minutos
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7 días

    private static final Key key = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(System.getenv("JWT_SECRET"))
    );

    // 🔐 Access Token: incluye datos personales
    public static String generateAccessToken(String givenName, String picture, String email, Set<Role> roles) {
        List<String> roleNames = roles.stream()
                .map(Role::getName)
                .toList();

        return Jwts.builder()
                .setSubject(email)
                .claim("given_name", givenName)
                .claim("picture", picture)
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }

    // 🔄 Refresh Token: solo email, vida más larga
    public static String generateRefreshToken(String email, String givenName, String picture) {
        return Jwts.builder()
                .setSubject(email)
                .claim("given_name", givenName)
                .claim("picture", picture)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }

    // ✅ Validación del token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 🧾 Extrae claims
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
