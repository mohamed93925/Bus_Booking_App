package com.BusBooking.BusBookingSystem.Service;

import com.BusBooking.BusBookingSystem.Model.Booking;
import com.BusBooking.BusBookingSystem.Model.Bus;
import com.BusBooking.BusBookingSystem.Model.User;
import com.BusBooking.BusBookingSystem.Repository.BookingRepository;
import com.BusBooking.BusBookingSystem.Repository.BusRepository;
import com.BusBooking.BusBookingSystem.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, BusRepository busRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.busRepository = busRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Booking book(Long userId, Long busId, int seats) {
        if (seats < 1) throw new IllegalArgumentException("At least one seat must be booked");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Bus bus = busRepository.findByIdForUpdate(busId)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found"));
        if (bus.getAvailableSeats() < seats) throw new IllegalArgumentException("Insufficient seats available");
        bus.setAvailableSeats(bus.getAvailableSeats() - seats);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setSeats(seats);
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        if (!userRepository.existsById(userId)) throw new IllegalArgumentException("User not found");
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public Booking cancel(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        if ("CANCELLED".equals(booking.getStatus())) throw new IllegalArgumentException("Booking is already cancelled");
        Bus bus = busRepository.findByIdForUpdate(booking.getBus().getId())
                .orElseThrow(() -> new IllegalArgumentException("Bus not found"));
        bus.setAvailableSeats(Math.min(bus.getTotalSeats(), bus.getAvailableSeats() + booking.getSeats()));
        booking.setStatus("CANCELLED");
        return booking;
    }
}
