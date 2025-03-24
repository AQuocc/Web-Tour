package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Controller
public class ContactController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/contact/send")
    @ResponseBody
    public ResponseEntity<?> sendMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message) {
        
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("anhquochunter@gmail.com"); // Email from contact.html
            mailMessage.setSubject("Contact Form: " + subject);
            mailMessage.setText(String.format(
                "Name: %s\nEmail: %s\n\nMessage:\n%s",
                name, email, message
            ));

            emailSender.send(mailMessage);
            
            return ResponseEntity.ok().body("{\"message\": \"Email sent successfully\"}");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to send email: " + e.getMessage() + "\"}");
        }
    }
}