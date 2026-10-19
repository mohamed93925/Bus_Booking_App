package com.BusBooking.BusBookingSystem.Service;

import com.BusBooking.BusBookingSystem.Model.Booking;
import com.BusBooking.BusBookingSystem.Model.Bus;
import com.BusBooking.BusBookingSystem.Model.User;
import com.BusBooking.BusBookingSystem.Repository.BookingRepository;
import com.BusBooking.BusBookingSystem.Repository.BusRepository;
import com.BusBooking.BusBookingSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,BusRepository busRepository,UserRepository userRepository){
        this.bookingRepository= bookingRepository;
        this.busRepository = busRepository;
        this.userRepository = userRepository;
    }

@Transactional
public Booking BookTicket(Long userId, Long busId, int seatBooked){

        if(seatBooked<=0){
            throw new IllegalArgumentException("Seat booked must be greater than zero");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User not Found"));

        Bus bus = busRepository.findById(busId)
                .orElseThrow(()-> new IllegalArgumentException("Bus not Found"));

        if(bus.getAvailableSeats() < seatBooked){
            throw new IllegalArgumentException("Not Enough seats Available");

        }

        bus.setAvailableSeats(bus.getAvailableSeats() - seatBooked);
        busRepository.save(bus);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setSeatsBooked(seatBooked);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus("Confirmed");

        return bookingRepository.save(booking);

}

@Transactional
public Booking cancelTicket(Long BookingId){

    Booking booking = bookingRepository.findById(BookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not Found"));

    if("Cancelled".equals(booking.getStatus())){
        throw new IllegalArgumentException("Already cancelled");

    }

    Bus bus = booking.getBus();
    bus.setAvailableSeats(bus.getAvailableSeats() + booking.getSeatsBooked());

    busRepository.save(bus);

    booking.setStatus("Cancelled");

    return bookingRepository.save(booking);


}

public List<Booking> getAllBookings(){
        return bookingRepository.findAll();

}
    public List<Booking> getBookingsByUserID(Long UserId){
        return bookingRepository.findUserById(UserId);
    }



}
