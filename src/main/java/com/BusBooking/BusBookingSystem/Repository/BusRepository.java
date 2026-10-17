package com.BusBooking.BusBookingSystem.Repository;

import com.BusBooking.BusBookingSystem.Model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus,Long> {

    List<Bus> findBySourceIgnoreCaseAndDestinationIgnoreCase(
            String source, String destination);


}
