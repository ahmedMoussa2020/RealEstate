package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.jpa.User;
import com.example.demo.repository.UserRepository;
import java.sql.Timestamp;
import java.time.Instant;
//This activity aims to implement a UserService class that contains business logic and calls UserDao to access the data stored in our User database table.
@Service
public class UserService {

	// The @Autowired annotation tells Spring to inject a UserDao instance into the
	// userDao variable:
	@Autowired // // instance variable called userDao
	UserRepository userRepository;
	
	    
	@Autowired
	EmailService emailService;

	// listUsers() method to get all the existing Users in the database:
	public List<User> listUsers() {

		return this.userRepository.findAll();
	}

	// findByUsername() method to get a user by username from the database:
	public Optional<User> findByUsername(String username) {

		return this.userRepository.findByUsername(username);
	}

	public void createUser(User user) {
		this.userRepository.save(user);
	}
	
	public User signup(User user){
		
		//  convert the username and emailId fields of the input user object to lowercase using the toLowerCase() 
		user.setUsername(user.getUsername().toLowerCase());
		user.setEmailId(user.getEmailId().toLowerCase());
		
		user.setEmailVerified(false);
		// Use the setCreatedOn method of the User object to set the createdOn field to the current timestamp using the Timestamp.from(Instant.now()) method.
		user.setCreatedOn(Timestamp.from(Instant.now()));
		// User object - The modified User object should then be saved to the database using the save() method of the UserRepository.
		this.userRepository.save(user);
	    
		this.emailService.sendVerificationEmail(user);
		
		return user;
	}
}