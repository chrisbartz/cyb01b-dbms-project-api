package com.cyb01b.dbmsprojectapi;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
	private List<String> errors = new ArrayList<String>();
	@SuppressWarnings("unused")
	private String error;
	
	public ErrorResponse(String error) {
		this.error = error;
		this.errors.add(error);
	}
	
	public ErrorResponse(List errors) {
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	

}
