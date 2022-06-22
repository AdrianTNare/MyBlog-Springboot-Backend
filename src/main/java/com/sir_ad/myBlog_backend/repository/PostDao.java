package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;
import java.util.UUID;

public interface PostDao {

    Optional<Post> addPost(UUID id, Post post);

    default Optional<Post> addPost(Post post){
        UUID id = UUID.randomUUID();
        return addPost(id, post);
    }

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByUser(User user, Pageable pageable);

    Optional<Post> findById(UUID id);

    Optional<Post> findByTitle(String title);

    int deletePost(UUID id);

    Optional<Post> updatePost(UUID id, Post post);

}
