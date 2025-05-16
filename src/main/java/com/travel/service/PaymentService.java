package com.travel.service;

import com.travel.model.Booking;
import com.travel.model.PaymentMethod;
import com.travel.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Autowired
    private BookingService bookingService;

    @Transactional
    public Booking initiatePayment(Long bookingId, PaymentMethod paymentMethod) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        if (booking.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Invalid payment status");
        }

        // Set payment information
        booking.setPaymentMethod(paymentMethod);
        booking.setTransactionId(generateTransactionId());
        
        return bookingService.updateBooking(booking);
    }

    @Transactional
    public Booking processPayment(Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        if (booking.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new RuntimeException("Invalid payment status");
        }

        if (booking.getPaymentMethod() == null) {
            throw new RuntimeException("Payment method not selected");
        }

        booking.setPaymentStatus(PaymentStatus.PROCESSING);

        // TODO: Integrate with actual payment gateway based on payment method
        // This is just a simulation for now
        try {
            // Simulate payment processing
            Thread.sleep(1000);
            
            // Assume payment is successful
            booking.setPaymentStatus(PaymentStatus.COMPLETED);
            booking.setPaymentDate(LocalDateTime.now());
            booking.setStatus("CONFIRMED");
            
            return bookingService.updateBooking(booking);
        } catch (Exception e) {
            booking.setPaymentStatus(PaymentStatus.FAILED);
            bookingService.updateBooking(booking);
            throw new RuntimeException("Payment processing failed: " + e.getMessage());
        }
    }

    private String generateTransactionId() {
        // Generate a unique transaction ID - this is just a simple implementation
        return "TXN" + System.currentTimeMillis();
    }

    public PaymentStatus checkPaymentStatus(Long bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }
        return booking.getPaymentStatus();
    }
}