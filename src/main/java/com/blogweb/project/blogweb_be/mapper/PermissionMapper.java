package com.blogweb.project.blogweb_be.mapper;

import org.mapstruct.Mapper;

import com.blogweb.project.blogweb_be.dto.request.permission.PermissionRequest;
import com.blogweb.project.blogweb_be.dto.respone.PermissionResponse;
import com.blogweb.project.blogweb_be.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission mapToPermission(PermissionRequest request);

    PermissionResponse mapToPermissionResponse(Permission permission);
}
