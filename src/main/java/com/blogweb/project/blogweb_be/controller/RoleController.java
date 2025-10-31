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

import com.blogweb.project.blogweb_be.dto.request.role.RoleRequest;
import com.blogweb.project.blogweb_be.dto.respone.ApiResponse;
import com.blogweb.project.blogweb_be.dto.respone.RoleResponse;
import com.blogweb.project.blogweb_be.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request) {

        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }

    @PutMapping("/{name}")
    ApiResponse<RoleResponse> updateRole(@PathVariable("name") String name, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.updateRole(name.toUpperCase(), request))
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> deleteRole(String name) {
        roleService.deleteRole(name);
        return ApiResponse.<Void>builder()
                .message("delete role successfully")
                .build();
    }
}
