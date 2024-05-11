package com.example.demo.service;

import java.sql.Timestamp;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.domain.FeedNotFoundException;
import com.example.demo.exception.domain.UserNotFoundException;
import com.example.demo.jpa.Feed;
import com.example.demo.jpa.User;
import com.example.demo.repository.FeedMetaDataRepository;
import com.example.demo.repository.FeedRepository;
import com.example.demo.repository.UserRepository;

@Service
public class FeedService {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;

	@Autowired
	FeedRepository feedRepository;

	@Autowired
	FeedMetaDataRepository feedMetaDataRepository;

	public Feed createFeed(Feed feed) {
		// here we retrieving the username of the currently logged-in user using
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// here we finding the user associated with the retrieved username from the
		// database
		// using methods. If the user is not found it throw a UserNotFoundException.
		User user = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
		// if the user exists in the DB, create the feed for the user
		feed.setUser(user);
		feed.setCreatedOn(Timestamp.from(Instant.now()));
		// Set the user Feed object to the retrieved and set the timstamp using the
		// current time
		// the feed object is created in the Feed table.
		return this.feedRepository.save(feed);
	}

	public Feed getFeedById(int feedId) {
		// This method will be responsible for getting a specific feed
		return this.feedRepository.findById(feedId)// this will return feedById from the CRUD repository
				.orElseThrow(() -> new FeedNotFoundException(String.format("Feed doesn't exist, %d", feedId)));
	}

}