package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.jpa.FeedMetaData;


// JpaRepository is a Spring Data interface that extends the CrudRepository interface and provides additional methods for working with JPA entities.
// { }: The body for the interface is empty since no additional methods are defined.
// Overall, the FeedMetaDataRepository interface extends JpaRepository and provides basic CRUD operations for the FeedMetaData entity class.
public interface FeedMetaDataRepository extends JpaRepository<FeedMetaData, Integer> {

}
