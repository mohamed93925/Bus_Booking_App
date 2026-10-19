package com.BusBooking.BusBookingSystem.Controller;

import com.BusBooking.BusBookingSystem.Model.Booking;
import com.BusBooking.BusBookingSystem.Service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public Booking bookTicket(@RequestParam Long userId,@RequestParam Long busId , @RequestParam int seatsBooked){
            try{
                return bookingService.BookTicket(userId , busId , seatsBooked);
            }catch(IllegalArgumentException exception){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());

            }

    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable("userId") Long userId){
        return bookingService.getBookingsByUserID(userId);

    }

    @PutMapping("/{bookingId}/cancel")
    public Booking cancelBooking(@PathVariable("bookingId") Long bookingId) {
        return bookingService.cancelTicket(bookingId);
    }




}
