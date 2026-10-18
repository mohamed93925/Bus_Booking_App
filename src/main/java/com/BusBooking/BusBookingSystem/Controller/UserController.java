package com.BusBooking.BusBookingSystem.Controller;

import com.BusBooking.BusBookingSystem.Model.User;
import com.BusBooking.BusBookingSystem.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;

    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user){
        try {
            return userService.registerUser(user);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }

    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
