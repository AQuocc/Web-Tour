package com.travel.service;

import com.travel.model.Booking;
import com.travel.model.Tour;
import com.travel.repository.BookingRepository;
import com.travel.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public List<Tour> getAllAvailableTours() {
        return tourRepository.findAll().stream()
                .filter(Tour::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Tour> getAvailableTours() {
        return getAllAvailableTours();
    }

    public Tour getTourById(Long id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
    }

    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour updateTour(Tour tour) {
        if (!tourRepository.existsById(tour.getId())) {
            throw new RuntimeException("Tour not found");
        }
        return tourRepository.save(tour);
    }

    @Transactional
    public void deleteTour(Long id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found"));
        
        // Delete all related bookings first
        List<Booking> bookings = bookingRepository.findByTourId(id);
        if (!bookings.isEmpty()) {
            bookings.forEach(booking -> bookingRepository.delete(booking));
        }
        
        // Then delete the tour
        tourRepository.delete(tour);
    }

    public List<Tour> searchTours(String destination, BigDecimal maxPrice, LocalDate startDate) {
        return tourRepository.findAll().stream()
                .filter(tour -> destination == null || 
                        tour.getDestination().toLowerCase().contains(destination.toLowerCase()))
                .filter(tour -> maxPrice == null || 
                        tour.getPrice().compareTo(maxPrice) <= 0)
                .filter(tour -> startDate == null || 
                        !tour.getStartDate().isBefore(startDate))
                .filter(Tour::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Tour> searchTours(String destination, Double maxPrice, String startDate) {
        BigDecimal maxPriceBigDecimal = maxPrice != null ? BigDecimal.valueOf(maxPrice) : null;
        LocalDate startDateParsed = null;
        if (startDate != null && !startDate.isEmpty()) {
            try {
                startDateParsed = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            } catch (Exception e) {
                throw new RuntimeException("Invalid date format. Please use YYYY-MM-DD");
            }
        }
        return searchTours(destination, maxPriceBigDecimal, startDateParsed);
    }

    public void updateTour(Long id, Tour updatedTour) {
        Tour existingTour = getTourById(id);
        existingTour.setName(updatedTour.getName());
        existingTour.setDescription(updatedTour.getDescription());
        existingTour.setDestination(updatedTour.getDestination());
        existingTour.setPrice(updatedTour.getPrice());
        existingTour.setDiscount(updatedTour.getDiscount());
        existingTour.setStartDate(updatedTour.getStartDate());
        existingTour.setEndDate(updatedTour.getEndDate());
        existingTour.setDuration(updatedTour.getDuration());
        existingTour.setMaxParticipants(updatedTour.getMaxParticipants());
        existingTour.setAvailable(updatedTour.isAvailable());
        existingTour.setImageUrl(updatedTour.getImageUrl());
        tourRepository.save(existingTour);
    }

    public boolean isTourAvailable(Long tourId, int numberOfParticipants) {
        Tour tour = getTourById(tourId);
        return tour.isAvailable() && tour.getMaxParticipants() >= numberOfParticipants;
    }
}