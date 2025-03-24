package com.travel.service;

import com.travel.model.Booking;
import com.travel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private BookingRepository bookingRepository;

    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // Get all confirmed bookings
        var bookings = bookingRepository.findByStatus("CONFIRMED");
        
        // Calculate total bookings
        statistics.put("totalBookings", bookings.size());
        
        // Calculate total revenue
        BigDecimal totalRevenue = bookings.stream()
            .map(Booking::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        statistics.put("totalRevenue", totalRevenue);
        
        // Calculate bookings per tour
        Map<String, Integer> bookingsPerTour = new HashMap<>();
        bookings.forEach(booking -> {
            String tourName = booking.getTour().getName();
            bookingsPerTour.merge(tourName, 1, Integer::sum);
        });
        statistics.put("bookingsPerTour", bookingsPerTour);

        // Calculate monthly revenue
        Map<String, BigDecimal> monthlyRevenue = new TreeMap<>(); // TreeMap to maintain month order
        
        // Initialize all months with zero
        for (Month month : Month.values()) {
            monthlyRevenue.put(
                month.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                BigDecimal.ZERO
            );
        }
        
        // Calculate revenue for each month
        bookings.forEach(booking -> {
            LocalDateTime bookingDate = booking.getBookingDate();
            String monthName = bookingDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            monthlyRevenue.merge(monthName, booking.getTotalPrice(), BigDecimal::add);
        });
        
        statistics.put("monthlyRevenue", monthlyRevenue);
        
        return statistics;
    }
}