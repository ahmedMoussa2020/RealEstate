package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.jpa.User;
import com.example.demo.repository.UserRepository;

//This activity aims to implement a UserService class that contains business logic and calls UserDao to access the data stored in our User database table.
@Service
public class UserService {

	// The @Autowired annotation tells Spring to inject a UserDao instance into the
	// userDao variable:
	@Autowired // // instance variable called userDao
	UserRepository userRepository;

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
}