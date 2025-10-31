package com.blogweb.project.blogweb_be.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.blogweb.project.blogweb_be.dto.request.role.RoleRequest;
import com.blogweb.project.blogweb_be.dto.respone.RoleResponse;
import com.blogweb.project.blogweb_be.exception.AppException;
import com.blogweb.project.blogweb_be.exception.ErrorCode;
import com.blogweb.project.blogweb_be.mapper.RoleMapper;
import com.blogweb.project.blogweb_be.repository.PermissionRepository;
import com.blogweb.project.blogweb_be.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.mapToRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.mapToRoleResponse(role);
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::mapToRoleResponse).toList();
    }

    public RoleResponse updateRole(String name, RoleRequest request) {
        var role = roleRepository.findById(name).orElseThrow(() -> new AppException(ErrorCode.Role_NOT_FOUND));
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        roleMapper.updateRoleFromRequest(role, request);
        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }

}
