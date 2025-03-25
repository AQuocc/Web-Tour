package com.travel.controller;

import com.travel.model.Tour;
import com.travel.service.TourService;
import com.travel.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public String listTours(Model model) {
        model.addAttribute("tours", tourService.getAllAvailableTours());
        return "tour/list";
    }

    @GetMapping("/{id}")
    public String tourDetails(@PathVariable Long id, Model model, Authentication authentication) {
        Tour tour = tourService.getTourById(id);
        tour.setRatings(ratingService.getTourRatings(id));
        model.addAttribute("tour", tour);
        
        if (authentication != null) {
            boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
        
        return "tour/details";
    }

    @GetMapping("/search")
    public String searchTours(@RequestParam(required = false) String destination,
                            @RequestParam(required = false) Double maxPrice,
                            @RequestParam(required = false) String startDate,
                            Model model) {
        model.addAttribute("tours", tourService.searchTours(destination, maxPrice, startDate));
        return "tour/list";
    }

    // Admin Operations
    @GetMapping("/admin/list")
    public String adminTourList(Model model) {
        model.addAttribute("tours", tourService.getAllTours());
        return "admin/tour/list";
    }

    @GetMapping("/admin/create")
    public String showCreateForm(Model model) {
        model.addAttribute("tour", new Tour());
        return "admin/tour/form";
    }

    @PostMapping("/admin/create")
    public String createTour(@ModelAttribute Tour tour,
                           @RequestParam("image") MultipartFile image,
                           RedirectAttributes redirectAttributes) {
        try {
            if (!image.isEmpty()) {
                String fileName = handleImageUpload(image);
                tour.setImageUrl("/images/" + fileName);
            }
            tourService.createTour(tour);
            redirectAttributes.addFlashAttribute("success", "Tour created successfully!");
            return "redirect:/tours/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "admin/tour/form";
        }
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("tour", tourService.getTourById(id));
        return "admin/tour/form";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateTour(@PathVariable Long id,
                           @ModelAttribute Tour tour,
                           @RequestParam(value = "image", required = false) MultipartFile image,
                           RedirectAttributes redirectAttributes) {
        try {
            if (image != null && !image.isEmpty()) {
                String fileName = handleImageUpload(image);
                tour.setImageUrl("/images/" + fileName);
            } else {
                // Keep existing image if no new image is uploaded
                Tour existingTour = tourService.getTourById(id);
                tour.setImageUrl(existingTour.getImageUrl());
            }
            tourService.updateTour(id, tour);
            redirectAttributes.addFlashAttribute("success", "Tour updated successfully!");
            return "redirect:/tours/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "admin/tour/form";
        }
    }

    private String handleImageUpload(MultipartFile image) throws IOException {
        // Generate unique filename
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        // Get the absolute path to the images directory
        String uploadDir = "src/main/resources/static/images";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the file
        try (InputStream inputStream = image.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteTour(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            tourService.deleteTour(id);
            redirectAttributes.addFlashAttribute("success", "Tour deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tours/admin/list";
    }
}