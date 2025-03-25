package com.travel.repository;

import com.travel.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByAvailableTrue();
    List<Tour> findByDestinationContainingIgnoreCase(String destination);
    List<Tour> findByPriceLessThanEqual(BigDecimal maxPrice);
    
    @Query("SELECT t FROM Tour t WHERE t.startDate >= :startDate AND t.available = true")
    List<Tour> findUpcomingTours(LocalDate startDate);
    
    List<Tour> findByDestinationAndStartDateGreaterThanEqual(String destination, LocalDate startDate);
}