package com.cyb01b.dbmsprojectapi;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class WebController {

	// Mock up a couple of endpoints for proof of concept
	@RequestMapping(method = RequestMethod.GET, path = "/authenticate")
    public ClientModel authenticate(
    		@RequestParam(value="userId") String userId, 
    		@RequestParam(value="password") String password) {
		System.out.print("An unauthenticated user is requesting the authenticate endpoint");
		ClientModel clientModel = new ClientModel();
		clientModel.setUserId(userId);
		clientModel.setResponse(password);
		clientModel.setAuthenticated(true);
        return clientModel;
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseObject hello(@RequestParam(value="name", defaultValue="World") String name) {
		String returnString = "Hello, " + name;
		System.out.print("An unauthenticated user is requesting the hello endpoint; response is: " + returnString);
		ResponseObject responseObject = new ResponseObject();
		responseObject.setResponseText(returnString);
        return responseObject;
    }
	
}
