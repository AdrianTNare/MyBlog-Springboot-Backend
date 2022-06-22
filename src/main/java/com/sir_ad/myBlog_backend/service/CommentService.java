package com.sir_ad.myBlog_backend.service;

import com.sir_ad.myBlog_backend.model.Comment;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {

    Optional<Comment> addComment(Comment comment);

    Page<Comment> findAll(int page);

    Page<Comment> findByPost(int page, Post post);

    Page<Comment> findByUser(int page, User user);

    Optional<Comment> findById(UUID id);

    String deleteComment(UUID id);

    Optional<Comment> updateComment(UUID id, Comment comment);
}
