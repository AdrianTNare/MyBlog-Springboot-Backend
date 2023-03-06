package com.sir_ad.myBlog_backend.service.impl;

import com.sir_ad.myBlog_backend.model.Role;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.RoleRepository;
import com.sir_ad.myBlog_backend.repository.UserRepository;
import com.sir_ad.myBlog_backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final String userRole = "ROLE_USER";

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }

    //  modify this function to use an existing role from the rolerepo
    @Override
    public void insertUser(User user) {
        if ((user.getUsername() != null) && (user.getEmail() != null) && (user.getPassword() != null)) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singletonList(roleRepository.findByRoleName(userRole)));
            userRepository.save(user);
        }
    }

    @Override
    public Page<User> findAll(int page) {
        return userRepository.findAll(PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }


    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public String deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
        User user = findByUsername(username);
        if (user == null) {
            return "success";
        }
        return null;
    }

    //  fix to first check if user is present in database
    @Override
    public Optional<User> updateUser(String userName, User user) {
        User updatedUser = null;
        User userPresent = findByUsername(userName);
        if (userPresent != null) {
            updatedUser = userPresent;
            updatedUser.setEmail(user.getEmail());
            updatedUser.setName(user.getName());
            updatedUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            updatedUser = userRepository.save(updatedUser);
        }
        return Optional.ofNullable(updatedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
//        List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles()
//                .stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
//                .collect(Collectors.toList());

        List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(Role::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
