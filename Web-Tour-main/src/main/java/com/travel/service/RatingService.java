package com.travel.service;

import com.travel.model.Rating;
import com.travel.model.Booking;
import com.travel.repository.RatingRepository;
import com.travel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashSet;

@Service
public class RatingService {
    
    @Autowired
    private RatingRepository ratingRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    public Rating createRating(Rating rating, Long bookingId) {
        if (ratingRepository.existsByBookingId(bookingId)) {
            throw new IllegalStateException("Rating already exists for this booking");
        }
        
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
            
        rating.setBooking(booking);
        rating.setTour(booking.getTour());
        rating.setUser(booking.getUser());
        
        return ratingRepository.save(rating);
    }

    public Set<Rating> getTourRatings(Long tourId) {
        return new HashSet<>(ratingRepository.findByTourId(tourId));
    }

    public boolean hasUserRatedBooking(Long bookingId) {
        return ratingRepository.existsByBookingId(bookingId);
    }
}