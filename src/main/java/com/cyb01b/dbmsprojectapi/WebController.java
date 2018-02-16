package com.cyb01b.dbmsprojectapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		System.out.print("An unauthenticated user is requesting the hello endpoint; response is: " + returnString);
		ResponseObject responseObject = new ResponseObject();
		responseObject.setResponseText(returnString);
        return responseObject;
    }
	
	// Endpoint that fetches customer information on login
	@RequestMapping(method = RequestMethod.GET, path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseObject login(@ModelAttribute RequestObject requestObject) throws Exception {
		System.out.print("An unauthenticated user is requesting the authenticate endpoint");
		ResponseObject responseObject = webService.getLogin(requestObject);
		return responseObject;
	}
	
	
	// EXCEPTION HANDLING
	
	@ExceptionHandler(Exception.class) 
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handle(Exception e) {
		System.err.println("An exception occurred" + e.getMessage());
		return new ErrorResponse("An exception occurred" + e.getMessage());
	}
	
	@ExceptionHandler(LoginException.class) 
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponse handle(LoginException e) {
		System.err.println("A login exception occurred" + e.getMessage());
		return new ErrorResponse("A login exception occurred" + e.getMessage());
	}
	
}
