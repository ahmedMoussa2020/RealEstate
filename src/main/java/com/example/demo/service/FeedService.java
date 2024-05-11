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
import com.example.demo.jpa.FeedMetaData;
import com.example.demo.jpa.User;
import com.example.demo.repository.FeedMetaDataRepository;
import com.example.demo.repository.FeedRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.example.demo.domain.PageResponse;
import java.util.Optional;
import com.example.demo.exception.domain.LikeExistException;



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

	
	public PageResponse<Feed> getUserFeeds(int pageNum, int pageSize) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
			
		User user = this.userRepository.findByUsername(username)
		             .orElseThrow(()-> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
					
		Page<Feed> paged = this.feedRepository.findByUser(user, PageRequest.of(pageNum, pageSize, Sort.by("feedId").descending()));
			
		return new PageResponse<Feed>(paged);
	}
	
	public PageResponse<Feed> getOtherUsersFeeds(int pageNum, int pageSize) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();		
		User user = this.userRepository.findByUsername(username)
		             .orElseThrow(()-> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
					
		Page<Feed> paged = this.feedRepository.findByUserNot(user, PageRequest.of(pageNum, pageSize, Sort.by("feedId").descending()));
			
		return new PageResponse<Feed>(paged);
	}
	
	public FeedMetaData createFeedMetaData(int feedId, FeedMetaData meta) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
		User user = this.userRepository.findByUsername(username)
					             .orElseThrow(()-> new UserNotFoundException(String.format("Username doesn't exist, %s", username)));
			
		Feed feed = this.feedRepository.findById(feedId)
					             .orElseThrow(()-> new FeedNotFoundException(String.format("Feed doesn't exist, %d", feedId)));

		FeedMetaData newMeta = new FeedMetaData();
			
		newMeta.setIsLike(false);
		newMeta.setUser(user);
		newMeta.setFeed(feed);
		newMeta.setCreatedOn(Timestamp.from(Instant.now()));
			
	    if (Optional.ofNullable(meta.getIsLike()).isPresent()) {
	        	
	        newMeta.setIsLike( meta.getIsLike() );
	            
	        if (meta.getIsLike()) {
	        		
	            feed.getFeedMetaData().stream()
	                      .filter(m -> m.getUser().getUsername().equals(username))
	      	              .filter(m -> m.getIsLike().equals(true)).findAny()
	      	              .ifPresent(m -> {throw new LikeExistException(String.format("Feed already liked, feedId: %d, username: %s", feedId, username));});
	            	
	            newMeta.setComment("");
	        }
	    } 
	        
	    if (!newMeta.getIsLike()) {
	        newMeta.setComment(meta.getComment());
	    }
	        
		return this.feedMetaDataRepository.save(newMeta);
	}
	
}