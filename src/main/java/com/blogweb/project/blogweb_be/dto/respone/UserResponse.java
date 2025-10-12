package com.blogweb.project.blogweb_be.dto.respone;

import java.util.List;
import java.util.Set;

import com.blogweb.project.blogweb_be.entity.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    String id;
    String username;
    String email;
    String password;

    Set<Role> roles;

    List<PostResponse> posts;

    List<CommentResponse> comments;
}
