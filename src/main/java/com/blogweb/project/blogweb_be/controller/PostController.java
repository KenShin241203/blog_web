package com.blogweb.project.blogweb_be.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogweb.project.blogweb_be.dto.request.post.PostCreateRequest;
import com.blogweb.project.blogweb_be.dto.request.post.PostUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.ApiResponse;
import com.blogweb.project.blogweb_be.dto.respone.PostResponse;
import com.blogweb.project.blogweb_be.service.PostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {

    PostService postService;

    @PostMapping
    ApiResponse<PostResponse> createPost(@RequestBody PostCreateRequest request) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }

    @PutMapping("{postId}")
    ApiResponse<PostResponse> updatePost(@PathVariable("postId") String postId, @RequestBody PostUpdateRequest request) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.updatePost(request, postId))
                .build();
    }

    @GetMapping
    ApiResponse<List<PostResponse>> getAllPost() {
        return ApiResponse.< List< PostResponse>>builder()
                .result(postService.getAllPost())
                .build();
    }

    @GetMapping("{postId}")
    ApiResponse<PostResponse> getPostById(@PathVariable("postId") String postId) {
        return ApiResponse.<PostResponse>builder()
                .result(postService.getPostById(postId))
                .build();
    }

    @DeleteMapping("{postId}")
    ApiResponse<Object> deletePost(@PathVariable("postId") String postId) {
        postService.deletePost(postId);
        return ApiResponse.builder()
                .message("Delete post with id:" + postId)
                .build();
    }
}
