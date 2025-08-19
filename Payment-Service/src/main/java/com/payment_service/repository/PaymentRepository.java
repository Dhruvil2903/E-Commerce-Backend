package com.payment_service.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payment_service.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	 Optional<Payment> findByPayerNameAndPaymentAmountAndPaymentDateBetween(
		        String payerName, Double paymentAmount, LocalDateTime startDate, LocalDateTime endDate
		    );
}
	