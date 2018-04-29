package com.cyb01b.dbmsprojectapi;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WebController {
	
	@Autowired 
	WebService webService;
	
	// REST ENDPOINTS
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject hello(@RequestParam(value="name", defaultValue="World") String name) {
		String returnString = "Hello, " + name;
		System.out.println("An unauthenticated user is requesting the hello endpoint; response is: " + returnString);
		ResponseObject responseObject = new ResponseObject();
		responseObject.setResponseText(returnString);
        return responseObject;
    }
	
	// Endpoint that fetches customer information on login, returns landing page
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject login(@Valid @RequestBody RequestObject requestObject) throws Exception {
		System.out.println("An unauthenticated user is requesting the authenticate endpoint");
		
		if (requestObject == null || requestObject.getUserName() == null)
			throw new LoginException("User name is null and cannot be authenticated!");
		
		ResponseObject responseObject = webService.getLogin(requestObject);
		return responseObject;
	}
	
	// Endpoint that fetches items matching search
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject search(@RequestParam(value="searchTerm") String searchTerm, @RequestParam(value="userName") String userName) throws SearchException {
		System.out.println("An unauthenticated user is requesting the search endpoint with search term:" + searchTerm);
		
		if (searchTerm == null || searchTerm.length() < 3)
			throw new SearchException("Search term must have 3 characters or more");
		
		if (searchTerm.contains(" "))
			throw new SearchException("Search term has invalid characters");
		
		ResponseObject responseObject = webService.getSearch(searchTerm, userName);
        return responseObject;
    }
	
	// Endpoint that fetches the homepage 
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/homepage", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject homePage(@RequestParam(value = "userName") String userName) throws Exception {
		System.out.println("An unauthenticated user is requesting the homepage endpoint with search term:");

		RequestObject requestObject = new RequestObject();
		requestObject.setUserName(userName);
		
		ResponseObject responseObject = webService.getHomepage(requestObject);
		return responseObject;
	}
	
	// Endpoint that accepts an order
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/submit_order", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject submitOrder(@Valid @RequestBody RequestObject requestObject) throws Exception {
		
		if (requestObject == null || requestObject.getUserName() == null)
			throw new LoginException("User name is null and cannot be authenticated!");
		
		System.out.println("User " + requestObject.getUserName() + " is submitting an order");

		ResponseObject responseObject = webService.getHomepage(requestObject);
		
		webService.submitOrder(requestObject, responseObject);
		
		return responseObject;
	}
	
	
	// EXCEPTION HANDLING
	
	@ExceptionHandler(Exception.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handle(Exception e) {
		e.printStackTrace();
		System.err.println("An exception occurred: " + e.getMessage());
		return new ErrorResponse("An exception occurred: " + e.getMessage());
	}
	
	@ExceptionHandler(LoginException.class) 
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handle(LoginException e) {
		e.printStackTrace();
		System.err.println("A login exception occurred: " + e.getMessage());
		return new ErrorResponse("Login failed: " + e.getMessage());
	}
	
	@ExceptionHandler(SearchException.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handle(SearchException e) {
		e.printStackTrace();
		System.err.println("A search exception occurred: " + e.getMessage());
		return new ErrorResponse("Search failed: " + e.getMessage());
	}
	
	@ExceptionHandler(SubmitOrderException.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handle(SubmitOrderException e) {
		e.printStackTrace();
		System.err.println("A submit order exception occurred: " + e.getMessage());
		return new ErrorResponse("Submit order failed: " + e.getMessage());
	}
	
}
