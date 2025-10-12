package com.blogweb.project.blogweb_be.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.blogweb.project.blogweb_be.dto.request.user.UserCreationRequest;
import com.blogweb.project.blogweb_be.dto.request.user.UserUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.UserResponse;
import com.blogweb.project.blogweb_be.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User mapToUser(UserCreationRequest request);

    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserMapper(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(target = "posts", source = "posts")
    @Mapping(target = "comments", source = "comments")
    UserResponse mapToUserResponse(User user);
}
