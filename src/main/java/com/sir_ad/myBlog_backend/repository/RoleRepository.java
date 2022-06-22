package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoleRepository extends CrudRepository<Role, UUID> {

    Role findByRoleName(String roleName);
}
