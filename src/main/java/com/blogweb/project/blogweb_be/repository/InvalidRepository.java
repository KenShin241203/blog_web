package com.blogweb.project.blogweb_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogweb.project.blogweb_be.entity.InvalidToken;

@Repository
public interface InvalidRepository extends JpaRepository<InvalidToken, String> {

}
