package com.BusBooking.BusBookingSystem.Service;


import com.BusBooking.BusBookingSystem.Model.User;
import com.BusBooking.BusBookingSystem.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    public User registerUser(User user){
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new IllegalArgumentException("A user with this email already exists");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();

    }

}
