package com.example.demo.config;

import com.example.demo.dtos.HotelDto;
import com.example.demo.dtos.RoomDto;
import com.example.demo.entities.Hotel;
import com.example.demo.entities.Role;
import com.example.demo.entities.Room;
import com.example.demo.entities.User;
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

@Configuration
@Profile("dev")
public class DatabaseConfig {
    @Bean
    CommandLineRunner initRolesAndSuperAdmin(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                Role roleAdmin = roleRepository.save(new Role("ROLE_USER"));
                User user = new User();
                user.setEmail("edelmiratest@gmail.com");
                user.setName("Edelmira");
                user.setCity("Paipa");
                user.setPhone("3132827258");
                Set<Role> roles = new HashSet<>();
                roles.add(roleAdmin);
                user.setRoles(roles);
                userRepository.save(user);
            }
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
            if (roleRepository.findByName("ROLE_VISITOR").isEmpty()) {
                roleRepository.save(new Role("ROLE_VISITOR"));
            }
        };
    }

    @Bean
    CommandLineRunner initHotelAndRooms(RoomRepository roomRepository, HotelRepository hotelRepository) {
        return args -> {
            HotelDto hotelDto = new HotelDto();
            hotelDto.setName("Villa");
            hotelDto.setAddress("Pantano de Vargas");
            hotelDto.setDescription("Hotel bonito ubicado en Paipa");
            hotelDto.setUrlImg("https://i.imgur.com/FG6h57i.jpeg");
            hotelRepository.save(new Hotel(hotelDto));
            // Buscar el hotel que creaste previamente
            Hotel hotel = hotelRepository.findByName("Villa");

            if (hotel != null) {
                RoomDto roomDto = new RoomDto();
                roomDto.setName("Suite Familiar");
                roomDto.setType("Suite");
                roomDto.setDescription("Habitación amplia para toda la familia");
                roomDto.setPrice(new BigDecimal("200000"));
                roomDto.setUrlImg("https://i.imgur.com/0JHzfkg.jpeg");
                roomDto.setCapacity(5);

                Room room = new Room(roomDto);
                room.setHotel(hotel); // Asignar el hotel
                roomRepository.save(room);
            } else {
                System.out.println("❌ Hotel no encontrado, no se creó la habitación.");
            }
        };
    }

}
