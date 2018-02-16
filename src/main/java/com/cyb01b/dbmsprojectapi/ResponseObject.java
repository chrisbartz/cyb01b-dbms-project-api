package com.cyb01b.dbmsprojectapi;

public class ResponseObject {
	private String responseText;
	private Customer customer; 

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
