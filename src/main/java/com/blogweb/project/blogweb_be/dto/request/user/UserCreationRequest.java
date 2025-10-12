package com.blogweb.project.blogweb_be.dto.request.user;

import java.time.LocalDate;
import java.util.Set;

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
public class UserCreationRequest {

    String username;
    String password;
    LocalDate dateOfBirth;

    Set<String> roles;

}
