package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.PageResponse;
import com.example.demo.jpa.Feed;
import com.example.demo.service.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin
@RestController
@RequestMapping("/feeds")
public class FeedController {
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired // this annotation inject an instance of FeedService into the class.
	FeedService feedService;

	@PostMapping
	public Feed createFeed(@RequestBody Feed feed) {
		// Call the createFeed() method of the feedService instance, passing in the feed
		// object as a parameter.
		logger.debug("Creating Feed");

		return this.feedService.createFeed(feed);
	}

	@GetMapping("/{feedId}")
	public Feed getFeed(@PathVariable int feedId) {

		logger.debug("Getting Feed, feedId: {}", feedId);

		return this.feedService.getFeedById(feedId);
	}
	
	@GetMapping("/user/{pageNum}/{pageSize}")
	public PageResponse<Feed> getUserFeeds(@PathVariable int pageNum, @PathVariable int pageSize) {
			
		logger.debug("Getting User Feeds List, pageNum: {}, pageSize: {}", pageNum, pageSize);
			
		return this.feedService.getUserFeeds(pageNum, pageSize);	
	}
}