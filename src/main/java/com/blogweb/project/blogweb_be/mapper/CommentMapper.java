package com.blogweb.project.blogweb_be.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.blogweb.project.blogweb_be.dto.request.comment.CommentCreationRequest;
import com.blogweb.project.blogweb_be.dto.request.comment.CommentUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.CommentResponse;
import com.blogweb.project.blogweb_be.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "post", ignore = true)
    Comment maptoComment(CommentCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCommentMapper(@MappingTarget Comment comment, CommentUpdateRequest request);

    CommentResponse maptoCommentResponse(Comment comment);

}
