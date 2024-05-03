package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

// This activity aims to implement a UserService class that contains business logic and calls UserDao to access the data stored in our User database table.

import org.springframework.stereotype.Service;

import com.example.demo.jdbc.UserBean;
import com.example.demo.jdbc.UserDao;

@Service
public class UserService {

	// The @Autowired annotation tells Spring to inject a UserDao instance into the
	// userDao variable:
	@Autowired
	// instance variable called userDao
	UserDao userDao;

	// listUsers() method to get all the existing Users in the database:
	public List<UserBean> listUsers() {

		return this.userDao.listUsers();
	}

	// findByUsername() method to get a user by username from the database:
	public UserBean findByUsername(String username) {

		return this.userDao.findByUsername(username);
	}

	public void createUser(UserBean user) {
		this.userDao.createUser(user);
	}
}