package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleDao {
    Optional<Role> findByRoleName(String roleName);

    Optional<Role> findById(UUID id);

    List<Role> findAllRoles();

    int insertRole(UUID id, Role role);

    default int insertRole(Role role){
        UUID id = UUID.randomUUID();
        return insertRole(id, role);
    }

    int deleteById(UUID id) ;

    int updateRole(UUID id, Role role);
}
