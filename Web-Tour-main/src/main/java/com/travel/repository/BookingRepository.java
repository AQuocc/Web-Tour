package com.travel.repository;

import com.travel.model.Booking;
import com.travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @org.springframework.data.jpa.repository.Query("SELECT b FROM Booking b JOIN FETCH b.tour WHERE b.user = :user")
    List<Booking> findByUserWithTour(User user);
    List<Booking> findByStatus(String status);
    List<Booking> findByUserAndStatus(User user, String status);
    boolean existsByTourIdAndUserAndStatus(Long tourId, User user, String status);
    List<Booking> findByTourId(Long tourId);
}