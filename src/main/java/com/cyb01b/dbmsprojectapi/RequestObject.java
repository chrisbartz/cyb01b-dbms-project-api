package com.cyb01b.dbmsprojectapi;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RequestObject {
	@NotNull private String userName;
	private String searchTerm;
	private List<Item> orderItems;
	
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
}
