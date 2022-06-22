package com.sir_ad.myBlog_backend.service;

import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PostService {

    Optional<Post> addPost(Post post);

    Page<Post> findAll(int page);

    Page<Post> findAllByUser(String username, int page);

    Optional<Post> findById(UUID id);

    Optional<Post> findByTitle(String title);

    String deletePost(UUID id);

    Optional<Post> updatePost(UUID id, Post post);

}
