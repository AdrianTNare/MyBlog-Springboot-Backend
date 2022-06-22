package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import java.util.UUID;

public interface UserDao {

    Optional<User> insertUser(UUID id, User user);
//    the user passed into the parameter is just a mapping of the httprequest object into
//    the user blueprint, it really doesnt contain an id yet , its getName used to create actual user
    default Optional<User> insertUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    Page<User> findAllUsers(Pageable pageable);

    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    Optional<User> findByUsername(String username);

    int deleteById(UUID id);

    Optional<User> updateUser(UUID id, User user);


}

