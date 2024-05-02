package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // annotation tells Spring that a class is a Controller and will process user
				// requests.
@RequestMapping("/user") // annotation maps requests to handlers. In this case, requests with the /user
							// parameter will be served by the UserController.
public class UserController {

//	This variable will be used from the UserController methods to print out the Controller's activity information in the console. 
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/test")
	public String testController() {

		// Call the logger's debug() method from the testController() method to print
		// out that the method was invoked, as shown below.
		logger.debug("The testController() method was invoked!");

		// Return the string "The FeedApp application is up and running" from the
		// testController() method, as shown below.
		return "The Web application is up and running";
	}
}