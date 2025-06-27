package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Reservation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    Reservation findByReference(String reference);
    @Modifying
    @Transactional
    @Query("UPDATE Reservation r SET r.status = :status WHERE r.reference = :reference")
    int updateStatusByReference(@Param("reference") String reference, @Param("status") String status);
}
