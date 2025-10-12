package com.blogweb.project.blogweb_be;

import org.springframework.boot.SpringApplication;

public class TestBlogwebBeApplication {

	public static void main(String[] args) {
		SpringApplication.from(BlogwebBeApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
