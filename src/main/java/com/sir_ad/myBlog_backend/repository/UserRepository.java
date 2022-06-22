package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    void deleteByUsername(String username);

}
