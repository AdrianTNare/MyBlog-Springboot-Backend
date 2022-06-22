package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.Comment;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends CrudRepository<Comment, UUID> {

    Page<Comment> findAllByOrderByDateCreatedDesc(Pageable pageable);

    Page<Comment> findAllByPostOrderByDateCreatedDesc(Post post, Pageable pageable);

    Page<Comment> findAllByUserOrderByDateCreatedDesc(User user, Pageable pageable);

    Optional<Comment> findById(UUID id);
}
