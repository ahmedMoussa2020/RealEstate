package com.example.demo.controller;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.jdbc.UserBean;
import com.example.demo.service.UserService;

@RestController // annotation tells Spring that a class is a Controller and will process user
				// requests.
@RequestMapping("/user") // annotation maps requests to handlers. In this case, requests with the /user
							// parameter will be served by the UserController.
public class UserController {

//	This variable will be used from the UserController methods to print out the Controller's activity information in the console. 
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	// The @Autowired annotation tells Spring to inject a UserService instance into
	// the userService variable:
	@Autowired
	UserService userService;

	@GetMapping("/test")
	public String testController() {

		// Call the logger's debug() method from the testController() method to print
		// out that the method was invoked, as shown below.
		logger.debug("The testController() method was invoked!");

		// Return the string "The FeedApp application is up and running" from the
		// testController() method, as shown below.
		return "The Web application is up and running";
	}

	@GetMapping("/")
	// listUsers() method to get all the existing Users in the database:
	public List<UserBean> listUsers() {
		logger.debug("The listUsers() method was invoked!");
		return this.userService.listUsers();
	}

	// findByUsername() method to get an existing User from the database:
	@GetMapping("/{username}")

	// The @PathVariable annotation handles template variables in the request URI
	// mapping and sets them as method parameters:
	public UserBean findByUsername(@PathVariable String username) {

		logger.debug("The findByUsername() method was invoked!, username={}", username);
		return this.userService.findByUsername(username);
	}

	// createUser() method to create a User in the database:
	// The @PathVariable annotation handles template variables in the request URI
	// mapping and sets them as method parameters:

	@GetMapping("/{first}/{last}/{username}/{password}/{phone}/{emailId}")
	public String createUser(@PathVariable String first, @PathVariable String last, @PathVariable String username,
			@PathVariable String password, @PathVariable String phone, @PathVariable String emailId) {

		UserBean user = new UserBean();

		user.setFirstName(first);
		user.setLastName(last);
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmailId(emailId);
		user.setEmailVerified(false);
		user.setCreatedOn(Timestamp.from(Instant.now()));

		logger.debug("The createUser() method was invoked!, user={}", user.toString());

		this.userService.createUser(user);

		return "User Created Successfully";
	}

}
