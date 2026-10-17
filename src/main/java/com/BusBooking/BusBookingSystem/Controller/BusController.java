package com.BusBooking.BusBookingSystem.Controller;


import com.BusBooking.BusBookingSystem.Model.Bus;
import com.BusBooking.BusBookingSystem.Service.BusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService){
        this.busService = busService;
    }

    @GetMapping
    public List<Bus> getAllBuses(){
        return busService.getAllBuses();
    }

    @PostMapping
    public Bus addBus(@RequestBody Bus bus){
        return busService.addBus(bus);

    }

    @GetMapping("/{id}")
    public Optional<Bus> getBusById(@PathVariable Long id) {
        return busService.getBusById(id);
    }

    @PutMapping("/{id}")
    public Bus updateBus(@PathVariable Long id, @RequestBody Bus bus){
        return busService.updateBus(id,bus);

    }

    @DeleteMapping("/{id}")
    public void deleteBus(@PathVariable Long id, @RequestBody Bus bus){
        busService.deleteBus(id);
    }

    @GetMapping("/search")
    public List<Bus> searchBuses(
            @RequestParam String source,
            @RequestParam String destination) {

        return busService.searchBuses(source, destination);
    }



}
