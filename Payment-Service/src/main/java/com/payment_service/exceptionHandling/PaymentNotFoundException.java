package com.payment_service.exceptionHandling;

public class PaymentNotFoundException extends RuntimeException {
	public PaymentNotFoundException(String message) {
		super(message);

	}

}
