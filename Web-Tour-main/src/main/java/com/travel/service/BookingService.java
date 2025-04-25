package com.travel.service;

import com.travel.model.Booking;
import com.travel.model.Tour;
import com.travel.model.User;
import com.travel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourService tourService;

    @Transactional
    public Booking createBooking(User user, Long tourId, int numberOfParticipants) {
        Tour tour = tourService.getTourById(tourId);
        
        // Validate booking
        if (!tour.isAvailable()) {
            throw new RuntimeException("Tour is not available for booking");
        }
        
        if (numberOfParticipants <= 0) {
            throw new RuntimeException("Number of participants must be greater than 0");
        }
        
        if (numberOfParticipants > tour.getMaxParticipants()) {
            throw new RuntimeException("Number of participants exceeds tour capacity");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTour(tour);
        booking.setNumberOfParticipants(numberOfParticipants);

        // Calculate price with discount
        BigDecimal basePrice = tour.getPrice();
        Integer discount = tour.getDiscount();
        int discountValue = (discount != null) ? discount.intValue() : 0;

        // Calculate total price with safe discount handling
        BigDecimal totalPrice = basePrice;
        if (discountValue > 0) {
            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                BigDecimal.valueOf(discountValue).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
            );
            totalPrice = basePrice.multiply(discountMultiplier);
        }

        // Apply number of participants and set scale
        totalPrice = totalPrice.multiply(BigDecimal.valueOf(numberOfParticipants))
            .setScale(2, RoundingMode.HALF_UP);
        booking.setTotalPrice(totalPrice);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional(readOnly = true)
    public List<Booking> getUserBookings(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        try {
            return bookingRepository.findByUserWithTour(user);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user bookings: " + e.getMessage());
        }
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getPendingBookings() {
        return bookingRepository.findByStatus("PENDING");
    }

    public List<Booking> getBookingsByStatus(String status) {
        if (status == null || status.isEmpty()) {
            return getAllBookings();
        }
        return bookingRepository.findByStatus(status.toUpperCase());
    }

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        if (!booking.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only pending bookings can be cancelled");
        }
        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    @Transactional
    public void updateBookingStatus(Long id, String status) {
        Booking booking = getBookingById(id);
        if (!isValidStatus(status)) {
            throw new RuntimeException("Invalid booking status");
        }
        booking.setStatus(status.toUpperCase());
        bookingRepository.save(booking);
    }

    public boolean isBookingBelongsToUser(Long bookingId, User user) {
        Booking booking = getBookingById(bookingId);
        return booking.getUser().getId().equals(user.getId());
    }

    private boolean isValidStatus(String status) {
        return status != null && (
            status.equalsIgnoreCase("PENDING") ||
            status.equalsIgnoreCase("CONFIRMED") ||
            status.equalsIgnoreCase("CANCELLED")
        );
    }
}