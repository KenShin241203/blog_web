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

import com.blogweb.project.blogweb_be.dto.request.comment.CommentCreationRequest;
import com.blogweb.project.blogweb_be.dto.request.comment.CommentUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.ApiResponse;
import com.blogweb.project.blogweb_be.dto.respone.CommentResponse;
import com.blogweb.project.blogweb_be.service.CommentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentService commentService;

    @GetMapping
    ApiResponse<List<CommentResponse>> getAllComent() {
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getAllComment())
                .build();
    }

    @PostMapping
    ApiResponse<CommentResponse> createComment(@RequestBody CommentCreationRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

    @GetMapping("{commentId}")
    ApiResponse<CommentResponse> getCommentById(@PathVariable("commentId") String commentId) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.getCommentById(commentId))
                .build();
    }

    @PutMapping("{commentId}")
    ApiResponse<CommentResponse> updateComment(@PathVariable("commentId") String commentId, @RequestBody CommentUpdateRequest request) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.updateComment(commentId, request))
                .build();
    }

    @DeleteMapping("{commentId}")
    ApiResponse<Object> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.deleteComment(commentId);
        return ApiResponse.builder()
                .message("Delete comment with id:" + commentId)
                .build();
    }
}
