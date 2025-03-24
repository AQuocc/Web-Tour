package com.travel.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "tours")
public class Tour {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private Integer discount = 0;

    private String destination;

    private String duration;

    private Integer maxParticipants;

    private String imageUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private boolean available = true;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private Set<Booking> bookings;
}