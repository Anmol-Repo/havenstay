package com.havenstay.repository;

import com.havenstay.entity.Booking;
import com.havenstay.entity.BookingReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingReferenceRepository extends JpaRepository<BookingReference, Long> {
    Optional<BookingReference> findByReferenceNo(String referenceNo);

    @Query("""
           select CASE
           when  count(b) = 0  then true
           else false
           end from Booking b 
           where b.room.id = :roomId
           AND   b.checkInDate<= :checkInDate
           AND   b.checkOutDate>= :checkOutDate
""")
    boolean isRoomAvailable(@Param("roomId") Long roomId,
                            @Param("checkInDate") LocalDate checkInDate,
                            @Param("checkOutDate") LocalDate checkOutDate
                             );
}
