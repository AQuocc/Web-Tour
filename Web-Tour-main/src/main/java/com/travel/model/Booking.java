package com.travel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Column(nullable = false)
    private Integer numberOfParticipants;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED

    private String specialRequirements;

    @Transient
    private boolean canRate;

    // Explicitly add getters for ID and Status
    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setCanRate(boolean canRate) {
        this.canRate = canRate;
    }
}