package com.BusBooking.BusBookingSystem.Controller;

import com.BusBooking.BusBookingSystem.Model.Booking;
import com.BusBooking.BusBookingSystem.Service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) { this.bookingService = bookingService; }

    public record CreateBookingRequest(@Min(value = 1, message = "A valid user ID is required") Long userId,
                                       @Min(value = 1, message = "A valid bus ID is required") Long busId,
                                       @Min(value = 1, message = "At least one seat must be booked") int seats) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking book(@Valid @RequestBody CreateBookingRequest request) {
        try {
            return bookingService.book(request.userId(), request.busId(), request.seats());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getUserBookings(@PathVariable Long userId) {
        try {
            return bookingService.getUserBookings(userId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{bookingId}/cancel")
    public Booking cancel(@PathVariable Long bookingId) {
        try {
            return bookingService.cancel(bookingId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
