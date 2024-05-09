package com.example.demo.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.domain.EmailExistException;
import com.example.demo.exception.domain.UsernameExistException;
import com.example.demo.jpa.User;
import com.example.demo.provider.ResourceProvider;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;


//This activity aims to implement a UserService class that contains business logic and calls UserDao to access the data stored in our User database table.
@Service
public class UserService {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtService jwtService;

	@Autowired
	ResourceProvider provider;

	public List<User> listUsers() {

		return this.userRepository.findAll();
	}

	public Optional<User> findByUsername(String username) {

		return this.userRepository.findByUsername(username);
	}

	public void createUser(User user) {

		this.userRepository.save(user);
	}

	public User signup(User user) {

		user.setUsername(user.getUsername().toLowerCase());
		user.setEmailId(user.getEmailId().toLowerCase());

		this.validateUsernameAndEmail(user.getUsername(), user.getEmailId());

		user.setEmailVerified(false);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setCreatedOn(Timestamp.from(Instant.now()));

		this.userRepository.save(user);

		this.emailService.sendVerificationEmail(user);

		return user;
}
	
	private void validateUsernameAndEmail(String username, String emailId) {

		this.userRepository.findByUsername(username).ifPresent(u -> {
			throw new UsernameExistException(String.format("Username already exists, %s", u.getUsername()));
		});

		this.userRepository.findByEmailId(emailId).ifPresent(u -> {
			throw new EmailExistException(String.format("Email already exists, %s", u.getEmailId()));
		});

}
}