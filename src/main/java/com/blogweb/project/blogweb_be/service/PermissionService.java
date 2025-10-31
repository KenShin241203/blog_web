package com.blogweb.project.blogweb_be.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.permission.PermissionRequest;
import com.blogweb.project.blogweb_be.dto.respone.PermissionResponse;
import com.blogweb.project.blogweb_be.mapper.PermissionMapper;
import com.blogweb.project.blogweb_be.repository.PermissionRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        var permission = permissionMapper.mapToPermission(request);
        return permissionMapper.mapToPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream().map(permissionMapper::mapToPermissionResponse).toList();
    }

    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
