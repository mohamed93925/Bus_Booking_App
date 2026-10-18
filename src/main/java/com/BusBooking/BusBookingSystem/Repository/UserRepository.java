    package com.BusBooking.BusBookingSystem.Repository;

    import com.BusBooking.BusBookingSystem.Model.User;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserRepository extends JpaRepository<User,Long> {
        boolean existsByEmailIgnoreCase(String email);

    }
