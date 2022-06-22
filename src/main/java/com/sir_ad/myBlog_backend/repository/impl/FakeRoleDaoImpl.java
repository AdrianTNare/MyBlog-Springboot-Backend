package com.sir_ad.myBlog_backend.repository.impl;

import com.sir_ad.myBlog_backend.model.Role;
import com.sir_ad.myBlog_backend.repository.RoleDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FakeRoleDaoImpl implements RoleDao {
    private static List<Role> DB = new ArrayList<>();

    @Override
    public Optional<Role> findByRoleName(String roleName) {
        return DB.stream()
                .filter(role -> role.getRoleName().equals(roleName))
                .findFirst();
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return DB.stream()
                .filter(role -> role.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Role> findAllRoles() {
        return DB;
    }

    @Override
    public int insertRole(UUID id, Role role) {
        DB.add(new Role(id, role.getRoleName()));
        return 1;
    }

    @Override
    public int deleteById(UUID id) {
        Optional<Role> role = findById(id);
        if(role.isEmpty()){
            return 0;
        }
        DB.remove(role.get());
        return 1;
    }

    @Override
    public int updateRole(UUID id, Role role) {
        return findById(id)
                .map( item -> {
                 int indexOfRoleToBeUpdated = DB.indexOf(item);
                 if(indexOfRoleToBeUpdated >= 0) {
                     DB.set(indexOfRoleToBeUpdated, new Role(id, role.getRoleName()));
                     return 1;
                 }
                 return 0;
                })
                .orElse(0);
    }
}
