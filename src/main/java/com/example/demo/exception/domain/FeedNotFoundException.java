package com.example.demo.exception.domain;


public class FeedNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FeedNotFoundException(String message) {
		super(message);
	}
}