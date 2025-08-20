package com.payment_service.dto;


public class PaymentResponse {

	private String paymentId;
	private String userId;
	private double amount;
	private String status;
	private String message;
	

	public PaymentResponse(String paymentId, String userId, double amount, String status, String message) {
		super();
		this.paymentId = paymentId;
		this.userId = userId;
		this.amount = amount;
		this.status = status;
		this.message = message;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
