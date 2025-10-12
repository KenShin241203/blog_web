package com.blogweb.project.blogweb_be.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.comment.CommentCreationRequest;
import com.blogweb.project.blogweb_be.dto.request.comment.CommentUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.CommentResponse;
import com.blogweb.project.blogweb_be.entity.Comment;
import com.blogweb.project.blogweb_be.entity.Post;
import com.blogweb.project.blogweb_be.entity.User;
import com.blogweb.project.blogweb_be.exception.AppException;
import com.blogweb.project.blogweb_be.exception.ErrorCode;
import com.blogweb.project.blogweb_be.mapper.CommentMapper;
import com.blogweb.project.blogweb_be.repository.CommentRepository;
import com.blogweb.project.blogweb_be.repository.PostRepository;
import com.blogweb.project.blogweb_be.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;
    PostRepository postRepository;

    public CommentResponse createComment(CommentCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        Comment comment = commentMapper.maptoComment(request);
        comment.setUser(user);
        comment.setPost(post);
        return commentMapper.maptoCommentResponse(commentRepository.save(comment));
    }

    public List<CommentResponse> getAllComment() {
        return commentRepository.findAll().stream().map(commentMapper::maptoCommentResponse).toList();
    }

    public CommentResponse getCommentById(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.Comment_NOT_FOUND));
        return commentMapper.maptoCommentResponse(comment);
    }

    public CommentResponse updateComment(String id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.Comment_NOT_FOUND));
        commentMapper.updateCommentMapper(comment, request);
        return commentMapper.maptoCommentResponse(commentRepository.save(comment));
    }

    public void deleteComment(String id) {
        commentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.Comment_NOT_FOUND));
        commentRepository.deleteById(id);
    }
}
