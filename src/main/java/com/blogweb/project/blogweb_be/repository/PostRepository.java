package com.blogweb.project.blogweb_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogweb.project.blogweb_be.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

}
