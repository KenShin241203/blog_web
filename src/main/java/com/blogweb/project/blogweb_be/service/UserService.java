package com.blogweb.project.blogweb_be.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.user.UserCreationRequest;
import com.blogweb.project.blogweb_be.dto.request.user.UserUpdateRequest;
import com.blogweb.project.blogweb_be.dto.respone.UserResponse;
import com.blogweb.project.blogweb_be.entity.User;
import com.blogweb.project.blogweb_be.exception.AppException;
import com.blogweb.project.blogweb_be.exception.ErrorCode;
import com.blogweb.project.blogweb_be.mapper.UserMapper;
import com.blogweb.project.blogweb_be.repository.RoleRepository;
import com.blogweb.project.blogweb_be.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.mapToUserResponse(user))
                .toList();
    }

    @PostAuthorize("returnObject.username == authentication.name or hasRole('ADMIN')")
    public UserResponse getUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.mapToUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        return userMapper.mapToUserResponse(userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.mapToUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roleUser = roleRepository.findById("USER");
        user.setRoles(new HashSet<>(roleUser.stream().toList()));
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUserMapper(user, request);
        // var roles = roleRepository.findAllById(request.getRoles());
        // if (!roles.isEmpty()) {
        //     user.setRoles(new HashSet<>(roles));
        // }
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
    }
}
