package com.example.demo.exception.domain;


public class FeedNotUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FeedNotUserException(String message) {
		super(message);
	}
}