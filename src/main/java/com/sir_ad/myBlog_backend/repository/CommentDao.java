package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.Comment;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CommentDao {
    Optional<Comment> addComment(UUID id, Comment comment);

    default Optional<Comment> addComment(Comment comment){
        UUID id = UUID.randomUUID();
        return addComment(id, comment);
    }

    Page<Comment> findAll(Pageable pageable);

    Page<Comment> findByPost(Post post, Pageable pageable);

    Page<Comment> findByUser(User user, Pageable pageable);

    Optional<Comment> findById(UUID id);

    int deleteComment(UUID id);

    Optional<Comment> updateComment(UUID id, Comment comment);
}
