package com.travel.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {
    
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private JavaMailSender emailSender;
    
    @Value("${contact.form.recipient-email}")
    private String recipientEmail;

    @PostMapping("/contact/send")
    public String sendMessage(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {
        
        logger.info("Receiving contact form submission from: {} <{}>", name, email);
        
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipientEmail);
            mailMessage.setFrom(email);
            mailMessage.setSubject("Contact Form: " + subject);
            mailMessage.setText(String.format(
                "Name: %s\nEmail: %s\n\nMessage:\n%s",
                name, email, message
            ));

            logger.debug("Attempting to send email to: {}", recipientEmail);
            emailSender.send(mailMessage);
            logger.info("Email sent successfully to: {}", recipientEmail);
            
            redirectAttributes.addFlashAttribute("successMessage", "Your message has been sent successfully!");
            return "redirect:/contact";
        } catch (Exception e) {
            logger.error("Failed to send email: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to send message. Please try again later.");
            return "redirect:/contact";
        }
    }
    /*@PostConstruct
    public void testSendMail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipientEmail);
            message.setSubject("Test Email");
            message.setText("Xin chào! Đây là email test khi khởi động ứng dụng.");

            emailSender.send(message);
            System.out.println("✅ Email test gửi thành công!");
        } catch (Exception e) {
            System.out.println("❌ Gửi email test thất bại:");
            e.printStackTrace();
        }
    }*/
}