package com.example.demo.domain;

import java.util.Date;

import org.springframework.http.HttpStatus;



public class HttpResponse {


	Date timeStamp;
	int httpStatusCode; // 200, 201, 400, 500
	HttpStatus httpStatus; //  object that stores the HTTP status of the response, such as OK, CREATED, BAD_REQUEST, or INTERNAL_SERVER_ERROR.
	String reason;
	String message;



	// The constructor initializes the timeStamp instance variable to the current date and time ,sets the values of the other instance variables to the values passed in as arguments.
	 public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
	        this.timeStamp = new Date();
	        this.httpStatusCode = httpStatusCode;
	        this.httpStatus = httpStatus;
	        this.reason = reason;
	        this.message = message;
	}



	public Date getTimeStamp() {
		return timeStamp;
	}



	public int getHttpStatusCode() {
		return httpStatusCode;
	}



	public HttpStatus getHttpStatus() {
		return httpStatus;
	}



	public String getReason() {
		return reason;
	}



	public String getMessage() {
		return message;
	}



	@Override
	public String toString() {
		return "HttpResponse [timeStamp=" + timeStamp + ", httpStatusCode=" + httpStatusCode + ", httpStatus="
				+ httpStatus + ", reason=" + reason + ", message=" + message + "]";
	}



}
