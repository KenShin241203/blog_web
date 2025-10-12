package com.blogweb.project.blogweb_be.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {
    //User
    USER_EXISTED(1001, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    //Post
    POST_NOT_FOUND(2001, "Post not found", HttpStatus.NOT_FOUND),
    //Tag
    Tag_EXISTED(4001, "Tag already exists", HttpStatus.BAD_REQUEST),
    //Comment
    Comment_NOT_FOUND(3001, "Comment not found", HttpStatus.NOT_FOUND),
    //Validation
    Dob_INVALID(1005, "Day of birth must be at least {min}", HttpStatus.BAD_REQUEST),
    //Authentication
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1007, "You are not authenticated", HttpStatus.UNAUTHORIZED),
    //other
    UNKNOW_EXCEPTION(9999, "Unknow exception", HttpStatus.INTERNAL_SERVER_ERROR),
    ENUMKEY_INVALID(6666, "Key invalid", HttpStatus.BAD_REQUEST);
    int code;
    String message;
    HttpStatus statusCode;
}
