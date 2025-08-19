package com.payment_service.service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment_service.dto.PaymentResponse;
import com.payment_service.entity.Payment;
import com.payment_service.entity.Status;
import com.payment_service.exceptionHandling.PayerExistException;
import com.payment_service.exceptionHandling.PaymentNotFoundException;

import com.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	public PaymentResponse createPayer(String payerName)
			throws PayerExistException, IllegalAccessException {

		if (payerName == null || payerName.trim().isEmpty()) {
			throw new IllegalArgumentException("User must not be empty");
		}

		double defaultAmount = 100.0; 
		
		LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
		LocalDateTime endOfTime = startOfDay.plusDays(1).minusNanos(1);

		Optional<Payment> checkpayerExist = paymentRepository.findByPayerNameAndPaymentAmountAndPaymentDateBetween(payerName,defaultAmount,
				startOfDay, endOfTime);

		if (checkpayerExist.isPresent()) {
			throw new PayerExistException("Payer is exist");
		}
		Payment payment2 = new Payment();

		payment2.setPayerName(payerName);
		payment2.setPaymentAmount(defaultAmount);
		payment2.setStatus(Status.PENDING);
		payment2.setPaymentDate(LocalDateTime.now());
		payment2.setTransactionId(UUID.randomUUID().toString());

		Payment savedPayment = paymentRepository.save(payment2);

		// Map to DTO
		PaymentResponse response = new PaymentResponse();
		response.setId(savedPayment.getId());
		response.setTransactionId(savedPayment.getTransactionId());
		response.setPayerName(savedPayment.getPayerName());
		response.setPaymentAmount(savedPayment.getPaymentAmount());
		response.setStatus(savedPayment.getStatus().name());
		response.setPaymentDate(savedPayment.getPaymentDate());

		return response;
//		return paymentRepository.save(payment2);
	}

	public PaymentResponse getPaymentStatus(int id) throws PaymentNotFoundException {
		Optional<Payment> foundPayment = paymentRepository.findById(id);

		if (foundPayment.isEmpty()) {
			throw new PaymentNotFoundException("Payment is not found");
		}

		Payment payment = foundPayment.get();

		PaymentResponse paymentResponse = new PaymentResponse();

		paymentResponse.setId(payment.getId());
		paymentResponse.setPayerName(payment.getPayerName());
		paymentResponse.setPaymentAmount(payment.getPaymentAmount());
		paymentResponse.setTransactionId(payment.getTransactionId());
		paymentResponse.setStatus(payment.getStatus().name());
		paymentResponse.setPaymentDate(payment.getPaymentDate());

		return paymentResponse;
	}

	public PaymentResponse updatePaymentStatus(int id) throws PaymentNotFoundException {
		Optional<Payment> foundPayment = paymentRepository.findById(id);

		if (foundPayment.isEmpty()) {
			throw new PaymentNotFoundException("Payment is not found");
		}
		Payment payment = foundPayment.get();

		payment.setStatus(Status.COMPLETED);

		// Save the updated payment
		Payment updatedPayment = paymentRepository.save(payment);
		PaymentResponse paymentResponse = new PaymentResponse();

		paymentResponse.setPayerName(updatedPayment.getPayerName());
		paymentResponse.setPaymentAmount(updatedPayment.getPaymentAmount());
		paymentResponse.setPaymentDate(updatedPayment.getPaymentDate());
		paymentResponse.setStatus(updatedPayment.getStatus().name());

		return paymentResponse;
	}
	
	public PaymentResponse deletePayment(int id) throws PaymentNotFoundException {
		Optional<Payment> foundPayment = paymentRepository.findById(id);
		
		if(foundPayment.isEmpty()) {
			throw new PaymentNotFoundException("Payment does not exist");
			
		}
		Payment deletePayment = foundPayment.get();
		paymentRepository.delete(deletePayment);
		
		PaymentResponse paymentResponse = new PaymentResponse();
	    paymentResponse.setPayerName(deletePayment.getPayerName());
	    paymentResponse.setPaymentAmount(deletePayment.getPaymentAmount());
	    paymentResponse.setPaymentDate(deletePayment.getPaymentDate());
	    paymentResponse.setStatus(deletePayment.getStatus().name());

	    return paymentResponse;
	}

}
