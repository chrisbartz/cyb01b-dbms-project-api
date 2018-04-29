package com.cyb01b.dbmsprojectapi;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RequestObject {
	@NotNull private String userName;
	private String userId;
	private String searchTerm;
	private List<Item> orderItems;
	private int addressId;
	private int paymentId;
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public int getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public List<Item> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<Item> orderItems) {
		this.orderItems = orderItems;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
