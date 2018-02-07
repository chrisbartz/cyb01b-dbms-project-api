package com.cyb01b.dbmsprojectapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	// Mock up a couple of endpoints for proof of concept
	@RequestMapping(method = RequestMethod.GET, path = "/authenticate")
    public ClientModel authenticate(
    		@RequestParam(value="userId") String userId, 
    		@RequestParam(value="password") String password) {
		ClientModel clientModel = new ClientModel();
		clientModel.setUserId(userId);
		clientModel.setResponse(password);
		clientModel.setAuthenticated(true);
        return clientModel;
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String hello(@RequestParam(value="name", defaultValue="World") String name) {
		String returnString = "Hello, " + name;
        return returnString;
    }
	
}
