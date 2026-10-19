package com.BusBooking.BusBookingSystem.Service;


import com.BusBooking.BusBookingSystem.Model.Booking;
import com.BusBooking.BusBookingSystem.Model.Bus;
import com.BusBooking.BusBookingSystem.Repository.BookingRepository;
import com.BusBooking.BusBookingSystem.Repository.BusRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BusService {

    private final BusRepository busRepository;

    public BusService(BusRepository busRepository){
        this.busRepository = busRepository;

    }

    public Bus addBus(Bus bus){
        return busRepository.save(bus);

    }

    public List<Bus> getAllBuses(){
        return busRepository.findAll();

    }

    public Optional<Bus> getBusById(Long id){
        return busRepository.findById(id);
    }

    public void deleteBus(Long id){
        busRepository.deleteById(id);

    }
    public Bus updateBus(Long id, Bus updatedBus) {

        Bus existingBus = busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        existingBus.setBusName(updatedBus.getBusName());
        existingBus.setSource(updatedBus.getSource());
        existingBus.setDestination(updatedBus.getDestination());
        existingBus.setTotalSeats(updatedBus.getTotalSeats());
        existingBus.setAvailableSeats(updatedBus.getAvailableSeats());
        existingBus.setFare(updatedBus.getFare());

        return busRepository.save(existingBus);
    }

    public List<Bus> searchBuses(String source, String destination) {
        return busRepository.findBySourceIgnoreCaseAndDestinationIgnoreCase(
                source, destination);
    }








}
