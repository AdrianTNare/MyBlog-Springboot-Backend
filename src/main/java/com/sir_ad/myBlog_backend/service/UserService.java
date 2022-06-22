package com.sir_ad.myBlog_backend.service;

import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.UserDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService extends UserDetailsService {

    void insertUser(User user);

    Page<User> findAll(int page);

    Optional<User> findById(UUID id);

    User findByUsername(String userName);

    String deleteByUsername(String username);

    Optional<User> updateUser(String userName, User user);


}
