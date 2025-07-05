package com.example.demo.config;

import com.example.demo.dtos.HotelDto;
import com.example.demo.dtos.RoomDto;
import com.example.demo.entities.*;
import com.example.demo.exepcions.ResourceNotFoundException;
import com.example.demo.repositories.HotelRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.RoomRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Profile("dev")
@Configuration
public class DatabaseConfig {

    @Bean
    CommandLineRunner initRolesAndSuperAdmin(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role("ROLE_USER"));
            }

            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }

            if (roleRepository.findByName("ROLE_VISITOR").isEmpty()) {
                roleRepository.save(new Role("ROLE_VISITOR"));
            }

            String adminEmail = "diegoooh2o@gmail.com";
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseThrow();

                User user = new User();
                user.setEmail(adminEmail);
                user.setName("Edelmira");
                user.setCity("Paipa");
                user.setPhone("3132827258");
                Set<Role> roles = new HashSet<>();
                roles.add(roleAdmin);
                user.setRoles(roles);
                userRepository.save(user);
            }
        };
    }

    @Bean
    CommandLineRunner initHotelAndRooms(RoomRepository roomRepository, HotelRepository hotelRepository) {
        return args -> {
            Hotel hotel = hotelRepository.findByName("Villa").orElse(null);
            if(hotel == null) {
                HotelDto hotelDto =
                        new HotelDto("Villa", "Paipa Pantano de Vargas", "https://i.imgur.com/0JHzfkg.jpeg", "Chimba de Hotel");
                hotel = new Hotel(hotelDto);
                hotelRepository.save(hotel);
            }

            String roomName = "Suite Familiar";
            if (!roomRepository.existsByNameAndHotel(roomName, hotel)) {
                Room room = getRoom(hotel);

                room.getImages().add(new RoomImage("https://i.imgur.com/0JHzfkg.jpeg", room));
                room.getImages().add(new RoomImage("https://i.imgur.com/fgRlkHG.jpeg", room));
                room.getImages().add(new RoomImage("https://i.imgur.com/k2TLD4O.jpeg", room));

                roomRepository.save(room);
            }
        };
    }

    private static Room getRoom(Hotel hotel) {
        RoomDto roomDto = new RoomDto();
        roomDto.setName("Suite Familiar");
        roomDto.setType("Suite");
        roomDto.setDescription("Habitación amplia para toda la familia");
        roomDto.setPrice(new BigDecimal("3400"));
        roomDto.setUrlImg("https://i.imgur.com/0JHzfkg.jpeg");
        roomDto.setCapacity(5);

        Room room = new Room(roomDto);
        room.setHotel(hotel);
        return room;
    }
}
