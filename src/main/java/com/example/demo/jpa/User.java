package com.example.demo.jpa;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // annotation marks this class as a persistent entity in the database
@Table(name = "\"User\"") // annotation is used to specify the name of the database table associated with
							// this entity
public class User implements Serializable { // Java that tells the compiler that the class is serializable.
// This is a unique identifier for the class version used in the serialization and deserialization processes.
	private static final long serialVersionUID = 1L;

	@Id // that the field is the primary key of the database table.
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This annotation specifies that the value of the primary key
														// is automatically generated by the database.
	@Column(name = "\"userId\"") // This annotation specifies the mapping of the annotated field to a column in
									// the database table.
	private Integer userId;

	@Column(name = "\"firstName\"")
	private String firstName;

	@Column(name = "\"lastName\"")
	private String lastName;

	private String username;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String phone;

	@Column(name = "\"emailId\"")
	private String emailId;

	@Column(name = "\"emailVerified\"")
	private Boolean emailVerified;

	@Column(name = "\"createdOn\"")
	private Timestamp createdOn;

	public User() {
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
				+ username + ", password=" + password + ", phone=" + phone + ", emailId=" + emailId + ", emailVerified="
				+ emailVerified + ", createdOn=" + createdOn + "]";
	}

}