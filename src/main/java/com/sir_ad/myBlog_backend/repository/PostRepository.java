package com.sir_ad.myBlog_backend.repository;

import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends CrudRepository<Post, UUID> {

    Page<Post> findAllByOrderByDateCreatedDesc(Pageable pageable);

    Page<Post> findAllByUserOrderByDateCreatedDesc(User user, Pageable pageable);

    Optional<Post> findById(UUID id);

    Optional<Post> findByTitle(String title);
}
