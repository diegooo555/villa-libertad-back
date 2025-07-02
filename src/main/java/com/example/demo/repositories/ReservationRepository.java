package com.example.demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Reservation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByUserIdAndStatus(UUID userId, String status);
    Reservation findByReference(String reference);

    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.status = :status WHERE r.reference = :reference")
    void updateStatusByReference(@Param("reference") String reference, @Param("status") String status);
}
