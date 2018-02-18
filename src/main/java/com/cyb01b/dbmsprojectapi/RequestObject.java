package com.cyb01b.dbmsprojectapi;

import javax.validation.constraints.NotNull;

public class RequestObject {
	@NotNull private String userName;
	private String searchTerm;
	
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
}
