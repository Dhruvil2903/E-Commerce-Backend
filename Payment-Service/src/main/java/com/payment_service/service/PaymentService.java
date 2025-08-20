package com.payment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment_service.dto.PaymentRequest;
import com.payment_service.dto.PaymentResponse;
import com.payment_service.exceptionHandling.*;
import com.payment_service.entity.Payment;
import com.payment_service.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create payment
    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus("PENDING");
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                "Payment created successfully"
        );
    }

    // Get payment status
    public PaymentResponse getPaymentStatus(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                "Payment retrieved successfully"
        );
    }

    // Update payment status
    public PaymentResponse updatePaymentStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        payment.setStatus(status);
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                "Payment status updated successfully"
        );
    }

    // Refund payment
    public PaymentResponse refundPayment(String paymentId) {
        Payment payment = paymentRepository.findByPaymentId(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        payment.setStatus("REFUNDED");
        payment.setUpdatedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                "Payment refunded successfully"
        );
    }
}
