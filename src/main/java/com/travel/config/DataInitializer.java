package com.travel.config;

import com.travel.model.Tour;
import com.travel.model.User;
import com.travel.repository.TourRepository;
import com.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@traveltour.com");
            admin.setFullName("System Administrator");
            admin.setRole("ROLE_ADMIN");
            admin.setEnabled(true);
            userRepository.save(admin);
        }

        // Create sample tours if no tours exist
        if (tourRepository.count() == 0) {
            // Tour 1: Hạ Long Bay
            Tour tour1 = new Tour();
            tour1.setName("Beautiful Ha Long Bay");
            tour1.setDescription("Explore the stunning limestone islands and emerald waters of Ha Long Bay, a UNESCO World Heritage site. " +
                    "Enjoy luxury cruise accommodation, kayaking, cave exploration, and fresh seafood.");
            tour1.setDestination("Ha Long Bay, Vietnam");
            tour1.setDuration("3 days 2 nights");
            tour1.setPrice(new BigDecimal("299.99"));
            tour1.setMaxParticipants(20);
            tour1.setStartDate(LocalDate.now().plusDays(7));
            tour1.setEndDate(LocalDate.now().plusDays(9));
            tour1.setAvailable(true);
            tour1.setImageUrl("/images/vinhhalong.jpg");
            tourRepository.save(tour1);

            // Tour 2: Sapa
            Tour tour2 = new Tour();
            tour2.setName("Sapa Mountain Trek");
            tour2.setDescription("Experience the breathtaking mountain landscapes and rich cultural heritage of Sapa. " +
                    "Trek through terraced rice fields, visit ethnic minority villages, and stay with local families.");
            tour2.setDestination("Sapa, Vietnam");
            tour2.setDuration("4 days 3 nights");
            tour2.setPrice(new BigDecimal("399.99"));
            tour2.setMaxParticipants(15);
            tour2.setStartDate(LocalDate.now().plusDays(14));
            tour2.setEndDate(LocalDate.now().plusDays(17));
            tour2.setAvailable(true);
            tour2.setImageUrl("/images/sapa.jpg");
            tourRepository.save(tour2);

            // Tour 3: Mekong Delta
            Tour tour3 = new Tour();
            tour3.setName("Mekong Delta Experience");
            tour3.setDescription("Discover the rich culture and daily life of the Mekong Delta. " +
                    "Visit floating markets, fruit orchards, and traditional villages while enjoying local cuisine.");
            tour3.setDestination("Mekong Delta, Vietnam");
            tour3.setDuration("2 days 1 night");
            tour3.setPrice(new BigDecimal("199.99"));
            tour3.setMaxParticipants(25);
            tour3.setStartDate(LocalDate.now().plusDays(21));
            tour3.setEndDate(LocalDate.now().plusDays(22));
            tour3.setAvailable(true);
            tour3.setImageUrl("/images/mekongdelta.jpg");
            tourRepository.save(tour3);
            
            
        }
    }
}