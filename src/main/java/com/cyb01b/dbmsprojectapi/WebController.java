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
	
}
