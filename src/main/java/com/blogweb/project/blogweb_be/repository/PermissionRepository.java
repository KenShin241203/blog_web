package com.blogweb.project.blogweb_be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogweb.project.blogweb_be.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {

}
