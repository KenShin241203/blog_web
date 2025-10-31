package com.blogweb.project.blogweb_be.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.blogweb.project.blogweb_be.dto.request.role.RoleRequest;
import com.blogweb.project.blogweb_be.dto.respone.RoleResponse;
import com.blogweb.project.blogweb_be.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role mapToRole(RoleRequest request);

    @Mapping(target = "permissions", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoleFromRequest(@MappingTarget Role role, RoleRequest request);

    RoleResponse mapToRoleResponse(Role role);
}
