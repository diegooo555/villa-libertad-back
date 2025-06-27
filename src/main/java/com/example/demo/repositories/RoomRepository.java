package com.example.demo.repositories;

import com.example.demo.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId);

    @Query("""
    SELECT r FROM Room r
    WHERE r.deleted = false
      AND r.capacity >= :guests
      AND r.id NOT IN (
        SELECT res.room.id FROM Reservation res
        WHERE res.checkOut >= :startDate AND res.checkIn <= :endDate AND res.status = 'CONFIRMED'
    )""")
    List<Room> findAvailableRooms(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("guests") int guests
    );

}
