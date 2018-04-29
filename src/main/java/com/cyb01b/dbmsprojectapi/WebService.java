package com.cyb01b.dbmsprojectapi;

import java.util.List;

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
		PageData landingPage = webDao.getLandingPageData(requestObject.getUserName());
		responseObject.setPageData(landingPage);
		
		responseObject.setOrders(getOrderHistory(requestObject.getUserName()));
		
		return responseObject;
	}

	public ResponseObject getSearch(String searchTerm, String userName) {
		ResponseObject responseObject = new ResponseObject();
		
		PageData landingPage = webDao.searchForItems(searchTerm, userName);
		responseObject.setPageData(landingPage);
		
		return responseObject;
	}
	
	public ResponseObject getHomepage(RequestObject requestObject) throws Exception {
		ResponseObject responseObject = new ResponseObject();
		
		Customer customer;
		customer = webDao.getCustomerData(requestObject.getUserName());
		
		if (customer == null || customer.getCustomerId().equals(null)) {
			throw new LoginException("Customer was not authenticated succesfully");
		}
		
		responseObject.setCustomer(customer);
		
		// Customer is authenticated here - send the page data
		PageData landingPage = webDao.getLandingPageData(requestObject.getUserName());
		responseObject.setPageData(landingPage);
		
		responseObject.setOrders(getOrderHistory(requestObject.getUserName()));
		
		return responseObject;
	}
	
	public ResponseObject submitOrder(RequestObject requestObject, ResponseObject responseObject) throws Exception {
		
		if (null == requestObject.getOrderItems() || requestObject.getOrderItems().size() <= 0)
			throw new SubmitOrderException("The order items list was null or empty");

		// Customer has submitted an order - persist it
		webDao.submitOrder(requestObject, responseObject, requestObject.getUserName());
		
		responseObject.setOrders(getOrderHistory(requestObject.getUserName()));
		
		return responseObject;
	}
	
	public List<Order> getOrderHistory(String userName) {
		return webDao.getOrderHistory(userName);
	}

}
