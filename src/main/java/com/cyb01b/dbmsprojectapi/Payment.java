package com.cyb01b.dbmsprojectapi;

public class Payment {
	private int paymentId;
	private int customerId;
	private Double cardNumber;
	private String name;
	private String expirationDate;
	private Double cvv;
	
	
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Double getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Double cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Double getCvv() {
		return cvv;
	}
	public void setCvv(Double cvv) {
		this.cvv = cvv;
	}

}
