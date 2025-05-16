package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/contact-form/send")
    public String sendMessage(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "message", required = true) String message,
            RedirectAttributes redirectAttributes) {
        
        try {
            System.out.println("=== Contact Form Submission ===");
            System.out.println("Form parameters received:");
            System.out.println("- Name: " + name);
            System.out.println("- Email: " + email);
            System.out.println("- Subject: " + subject);
            System.out.println("- Message length: " + (message != null ? message.length() : 0));

            // Validate inputs
            if (name == null || email == null || subject == null || message == null) {
                throw new IllegalArgumentException("All fields are required");
            }

            // Create and configure mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("anhquochunter@gmail.com");
            mailMessage.setFrom("anhquochunter@gmail.com");
            mailMessage.setReplyTo(email);
            mailMessage.setSubject("Contact Form: " + subject);
            mailMessage.setText(String.format(
                "Contact Form Submission\n\n" +
                "Name: %s\n" +
                "Email: %s\n\n" +
                "Message:\n%s",
                name, email, message
            ));

            try {
                System.out.println("Sending email from " + name + " (" + email + ")");
                emailSender.send(mailMessage);
                System.out.println("✅ Email sent successfully!");
                redirectAttributes.addFlashAttribute("success",
                    "Thank you for your message. We'll contact you soon!");
                return "redirect:/contact-form";
                
            } catch (Exception e) {
                System.err.println("❌ Failed to send email: " + e.getMessage());
                e.printStackTrace();
                
                redirectAttributes.addFlashAttribute("error",
                    "Failed to send message. Please try again later.");
                return "redirect:/contact-form";
            }
        } catch (Exception e) {
            String errorMessage = "Failed to process request: " + e.getMessage();
            System.err.println("❌ " + errorMessage);
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/contact-form";
        }
    }

    @GetMapping("/contact-form")
    public String showContactForm() {
        return "contact";
    }

}
