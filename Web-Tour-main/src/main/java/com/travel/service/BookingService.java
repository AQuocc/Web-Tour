package com.travel.service;

import com.travel.model.Booking;
import com.travel.model.Tour;
import com.travel.model.User;
import com.travel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
//import java.util.stream.Collectors;

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
        BigDecimal priceAfterDiscount = tour.getDiscount() > 0
            ? tour.getPrice().multiply(BigDecimal.valueOf(100 - tour.getDiscount())).divide(BigDecimal.valueOf(100))
            : tour.getPrice();
        booking.setTotalPrice(priceAfterDiscount.multiply(BigDecimal.valueOf(numberOfParticipants)));
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<Booking> getUserBookings(User user) {
        return bookingRepository.findByUser(user);
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