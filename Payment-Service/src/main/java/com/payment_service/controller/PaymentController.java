package com.payment_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.payment_service.dto.*;
import com.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.status(201).body(paymentService.createPayment(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(paymentId));
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable String paymentId,
            @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponse> refundPayment(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }
}
