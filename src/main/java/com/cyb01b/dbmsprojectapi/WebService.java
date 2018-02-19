package com.cyb01b.dbmsprojectapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebService {
	
	@Autowired
	WebDao webDao;

	public ResponseObject getLogin(RequestObject requestObject) throws Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Customer customer;
		customer = webDao.getCustomerData(requestObject.getUserName());
		
		if (customer == null || customer.getCustomerId().equals(null)) {
			throw new LoginException("Customer was not authenticated succesfully");
		}
		
		responseObject.setCustomer(customer);
		
		// Customer is authenticated here - send the page data
		PageData landingPage = webDao.getLandingPageData();
		responseObject.setPageData(landingPage);
		
		return responseObject;
	}

}
