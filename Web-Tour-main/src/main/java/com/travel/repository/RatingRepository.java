package com.travel.repository;

import com.travel.model.Rating;
//import com.travel.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByTourId(Long tourId);
    boolean existsByBookingId(Long bookingId);
}