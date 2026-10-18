package com.BusBooking.BusBookingSystem.Repository;

import com.BusBooking.BusBookingSystem.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
}
