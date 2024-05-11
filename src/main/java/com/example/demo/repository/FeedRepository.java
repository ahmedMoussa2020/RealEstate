package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.jpa.Feed;
import com.example.demo.jpa.User;


// JpaRepository is a Spring Data interface that extends the CrudRepository interface and provides additional methods for working with JPA entities.
//  PagingAndSortingRepository is a Spring Data interface that extends the CrudRepository interface and provides additional methods for working with paginated and sorted data. It supports easy pagination and sorting by using Pageable and Sort parameters in repository methods.
public interface FeedRepository extends JpaRepository<Feed, Integer>, PagingAndSortingRepository<Feed, Integer> {

	Page<Feed> findByUser(User user, Pageable pageable);
	
	Page<Feed> findByUserNot(User user, Pageable pageable);
}


