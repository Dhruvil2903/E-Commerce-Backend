package com.payment_service.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment_service.dto.PaymentResponse;

import com.payment_service.exceptionHandling.PayerExistException;
import com.payment_service.exceptionHandling.PaymentNotFoundException;
import com.payment_service.service.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/createPayment")
	public ResponseEntity<?> createPayer(@RequestBody String payment) throws IllegalAccessException {
		try {
			PaymentResponse createPayer = paymentService.createPayer(payment);
			return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(createPayer);
		} catch (PayerExistException e) {
			return ResponseEntity.status(org.springframework.http.HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPayerStatus(@PathVariable int id) {
		try {
			PaymentResponse getPayer = paymentService.getPaymentStatus(id);
			return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(getPayer);
		} catch (PaymentNotFoundException e) {
			return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable int id) {
		try {
			PaymentResponse updateStatus = paymentService.updatePaymentStatus(id);
			return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(updateStatus);
		} catch (PaymentNotFoundException e) {
			return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePayment(@PathVariable int id) {
		try {
			PaymentResponse deletePayment = paymentService.deletePayment(id);
			return ResponseEntity.status(org.springframework.http.HttpStatus.OK).body(deletePayment);
		} catch (PaymentNotFoundException e) {
			return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
