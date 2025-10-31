package com.blogweb.project.blogweb_be.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.post.PostCreateRequest;
import com.blogweb.project.blogweb_be.dto.request.post.PostUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.PostResponse;
import com.blogweb.project.blogweb_be.entity.Post;
import com.blogweb.project.blogweb_be.entity.Tag;
import com.blogweb.project.blogweb_be.entity.User;
import com.blogweb.project.blogweb_be.exception.AppException;
import com.blogweb.project.blogweb_be.exception.ErrorCode;
import com.blogweb.project.blogweb_be.mapper.PostMapper;
import com.blogweb.project.blogweb_be.repository.PostRepository;
import com.blogweb.project.blogweb_be.repository.TagRepository;
import com.blogweb.project.blogweb_be.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    PostRepository postRepository;
    PostMapper postMapper;
    UserRepository userRepository;
    TagRepository tagRepository;

    @PreAuthorize("hasAuthority('CREATE_BLOG')")
    public PostResponse createPost(PostCreateRequest request) {
        log.info("Create new Post");
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Tag> tag = tagRepository.findAllById(request.getTagName());
        Post post = postMapper.maptoPost(request);
        post.setTags(new HashSet<>(tag));
        post.setUser(user);

        return postMapper.mapToPostResponse(postRepository.save(post));

    }

    public PostResponse updatePost(PostUpdateRequest request, String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        List<Tag> tag = tagRepository.findAllById(request.getTagName());
        post.setTags(new HashSet<>(tag));
        postMapper.updatePostMapper(post, request);
        return postMapper.mapToPostResponse(postRepository.save(post));
    }

    public List<PostResponse> getAllPost() {
        return postRepository.findAll().stream().map(postMapper::mapToPostResponse).toList();
    }

    public PostResponse getPostById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        return postMapper.mapToPostResponse(post);
    }

    public void deletePost(String postId) {
        postRepository.findById(postId).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        postRepository.deleteById(postId);
    }
}
