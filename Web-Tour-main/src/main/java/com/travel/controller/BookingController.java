package com.travel.controller;

import com.travel.model.Booking;
import com.travel.model.Rating;
import com.travel.model.User;
import com.travel.service.BookingService;
import com.travel.service.RatingService;
import com.travel.service.TourService;
import com.travel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TourService tourService;

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/create/{tourId}")
    public String showBookingForm(@PathVariable Long tourId, Model model, Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "error/403";
        }
        
        model.addAttribute("tour", tourService.getTourById(tourId));
        return "booking/create";
    }

    @PostMapping("/create/{tourId}")
    public String createBooking(@PathVariable Long tourId,
                              @RequestParam int numberOfParticipants,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "error/403";
            }

            User user = userService.findByUsername(authentication.getName());
            Booking booking = bookingService.createBooking(user, tourId, numberOfParticipants);
            redirectAttributes.addFlashAttribute("success", "Booking created successfully!");
            return "redirect:/bookings/" + booking.getId();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/tours/" + tourId;
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public String bookingDetails(@PathVariable Long id,
                                Authentication authentication,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        
        try {
            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                throw new RuntimeException("Booking not found");
            }
            
            User user = userService.findByUsername(authentication.getName());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            if (!isAdmin && !bookingService.isBookingBelongsToUser(id, user)) {
                return "error/403";
            }

            model.addAttribute("booking", booking);
            model.addAttribute("user", user);
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("availableStatuses", List.of("PENDING", "CONFIRMED", "CANCELLED"));
            model.addAttribute("canRate", !ratingService.hasUserRatedBooking(id) && booking.getStatus().equals("CONFIRMED"));
            
            return "booking/details";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return isAdmin ? "redirect:/bookings/admin/all" : "redirect:/bookings/my-bookings";
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-bookings")
    public String myBookings(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName());
        List<Booking> bookings = bookingService.getUserBookings(user);
        for (Booking booking : bookings) {
            booking.setCanRate(!ratingService.hasUserRatedBooking(booking.getId()) && 
                             "CONFIRMED".equals(booking.getStatus()));
        }
        model.addAttribute("bookings", bookings);
        return "booking/my-bookings";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName());
            if (!bookingService.isBookingBelongsToUser(id, user)) {
                return "error/403";
            }

            bookingService.cancelBooking(id);
            redirectAttributes.addFlashAttribute("success", "Booking cancelled successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/bookings/my-bookings";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/rate")
    public String showRatingForm(@PathVariable Long id, Model model, Authentication authentication) {
        User user = userService.findByUsername(authentication.getName());
        Booking booking = bookingService.getBookingById(id);
        
        if (!bookingService.isBookingBelongsToUser(id, user) || 
            !booking.getStatus().equals("CONFIRMED") || 
            ratingService.hasUserRatedBooking(id)) {
            return "error/403";
        }
        
        model.addAttribute("booking", booking);
        model.addAttribute("rating", new Rating());
        return "booking/rate";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/rate")
    public String submitRating(@PathVariable Long id,
                             @ModelAttribute Rating rating,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(authentication.getName());
            Booking booking = bookingService.getBookingById(id);
            
            if (!bookingService.isBookingBelongsToUser(id, user) || 
                !booking.getStatus().equals("CONFIRMED") || 
                ratingService.hasUserRatedBooking(id)) {
                return "error/403";
            }

            ratingService.createRating(rating, id);
            redirectAttributes.addFlashAttribute("success", "Rating submitted successfully!");
            return "redirect:/bookings/my-bookings";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookings/" + id + "/rate";
        }
    }

    // Admin Operations
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public String allBookings(@RequestParam(required = false) String status, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Booking> bookings;
            if (status != null && !status.isEmpty()) {
                if (!isValidStatus(status)) {
                    redirectAttributes.addFlashAttribute("error", "Invalid status filter");
                    return "redirect:/bookings/admin/all";
                }
                bookings = bookingService.getBookingsByStatus(status.toUpperCase());
            } else {
                bookings = bookingService.getAllBookings();
            }
            model.addAttribute("bookings", bookings);
            return "admin/booking/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error loading bookings: " + e.getMessage());
            return "redirect:/";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/pending")
    public String pendingBookings(Model model) {
        model.addAttribute("bookings", bookingService.getPendingBookings());
        return "admin/booking/pending";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/{id}/status")
    public String updateBookingStatus(@PathVariable Long id,
                                      @RequestParam String status,
                                      RedirectAttributes redirectAttributes) {
        try {
            if (status == null || status.trim().isEmpty()) {
                throw new IllegalArgumentException("Status cannot be empty");
            }
            
            String upperStatus = status.trim().toUpperCase();
            if (!isValidStatus(upperStatus)) {
                throw new IllegalArgumentException("Invalid status value: " + status);
            }

            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                throw new IllegalArgumentException("Booking not found");
            }
            
            bookingService.updateBookingStatus(id, upperStatus);
            
            String statusText = upperStatus.substring(0, 1) + upperStatus.substring(1).toLowerCase();
            redirectAttributes.addFlashAttribute("success",
                String.format("Booking #%d has been %s successfully", id, statusText));

            return "redirect:/bookings/" + id;

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookings/admin/all";
        }
    }

    private boolean isValidStatus(String status) {
        return status != null && (
            status.equalsIgnoreCase("PENDING") ||
            status.equalsIgnoreCase("CONFIRMED") ||
            status.equalsIgnoreCase("CANCELLED")
        );
    }
}