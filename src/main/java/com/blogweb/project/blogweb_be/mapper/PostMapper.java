package com.blogweb.project.blogweb_be.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.blogweb.project.blogweb_be.dto.request.post.PostCreateRequest;
import com.blogweb.project.blogweb_be.dto.request.post.PostUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.PostResponse;
import com.blogweb.project.blogweb_be.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "user", ignore = true)
    Post maptoPost(PostCreateRequest request);

    @Mapping(target = "tags", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePostMapper(@MappingTarget Post post, PostUpdateRequest request);

    @Mapping(target = "comments", source = "comments")
    PostResponse mapToPostResponse(Post post);
}
