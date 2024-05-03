package com.example.demo.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	// an instance variable called logger ,
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	// The @Autowired annotation tells Spring to inject a JdbcTemplate instance into
	// a jdbcTemplate variable:
	@Autowired
	// JdbcTemplate is a central class in the Spring JDBC core package that
	// simplifies the use of JDBC and helps to avoid common errors.
	JdbcTemplate jdbcTemplate;

	// listUsers() method to get all the existing Users in the database:
	public List<UserBean> listUsers() {

		String sql = "SELECT * FROM \"User\"";

		return this.jdbcTemplate.query(sql, new UserMapper());
	}

	// findByUsername() method to get a user by username from the database.
	public UserBean findByUsername(String username) {

		String sql = "SELECT * FROM \"User\" WHERE username = ?";

		List<UserBean> users = this.jdbcTemplate.query(sql, new UserMapper(), username);

		/*
		 * Returns null if users is empty otherwise, returns the first element in the
		 * list
		 */
		return users.isEmpty() ? null : users.get(0);
	}

	// createUser() method to create a user in the database:
	public void createUser(UserBean user) {

		String sql = "INSERT INTO \"User\" (\"firstName\", \"lastName\", username, phone, \"emailId\", password, \"emailVerified\", \"createdOn\") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		logger.debug("Insert Query: {}", sql);

		/* Executes the Insert Statement */
		this.jdbcTemplate.update(sql, new Object[] { user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getPhone(), user.getEmailId(), user.getPassword(), user.getEmailVerified(), user.getCreatedOn() });

	}

}
