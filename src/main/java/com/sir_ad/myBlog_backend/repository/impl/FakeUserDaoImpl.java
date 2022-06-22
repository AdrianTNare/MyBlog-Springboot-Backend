package com.sir_ad.myBlog_backend.repository.impl;

import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.UserDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeUserDaoImpl implements UserDao {

    private static List<User> DB = new ArrayList<>();

    @Override
    public Optional<User> insertUser(UUID id, User user) {
        DB.add(new User(id, user.getUsername(), user.getEmail(), user.getName(), user.getLastname()));
        return findById(id);
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return  usersToPage(DB, pageable);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return DB.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return DB.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return DB.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public int deleteById(UUID id) {
        Optional<User> user = findById(id);
        if(user.isEmpty()){
        return 0;
        }
        DB.remove(user.get());
        return 1;
    }

    @Override
    public Optional<User> updateUser(UUID id, User user) {
         return findById(id)
                .map(item -> {
                    User updatedUser = null;
                    int indexOfUserToUpdate = DB.indexOf(item);
                    if(indexOfUserToUpdate >=0 ) {
                        User updateUser = new User(id, user.getUsername(), user.getEmail(), user.getName(), user.getLastname());
                        updateUser.setPassword(user.getPassword());
                        DB.set(indexOfUserToUpdate, updateUser);
                        updatedUser = findById(id).orElse(null);
                    }
                    return updatedUser;
                });
    }

    Page<User> usersToPage(List<User> userList, Pageable pageable){
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), userList.size());
        if(start > userList.size()){
            return new PageImpl<>(new ArrayList<>(), pageable, userList.size());
        }
        return new PageImpl<>(userList.subList(start, end), pageable, userList.size());
    }
}
