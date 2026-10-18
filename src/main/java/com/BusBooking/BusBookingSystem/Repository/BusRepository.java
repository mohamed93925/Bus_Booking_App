package com.BusBooking.BusBookingSystem.Repository;

import com.BusBooking.BusBookingSystem.Model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Bus b where b.id = :id")
    java.util.Optional<Bus> findByIdForUpdate(@Param("id") Long id);

    List<Bus> findBySourceIgnoreCaseAndDestinationIgnoreCase(
            String source, String destination);


}
