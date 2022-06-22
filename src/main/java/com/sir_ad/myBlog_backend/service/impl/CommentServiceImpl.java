package com.sir_ad.myBlog_backend.service.impl;

import com.sir_ad.myBlog_backend.model.Comment;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.*;
import com.sir_ad.myBlog_backend.service.CommentService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private  int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1;
    }
    @Override
    public Optional<Comment> addComment(Comment comment) {
        Comment newComment = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Claims claims = (Claims) authentication.getPrincipal();
            String currentUserName = claims.getSubject();
            User user = userRepository.findByUsername(currentUserName);
            Optional<Post> post = postRepository.findById(comment.getPost().getId());
            if(user != null && post.isPresent()){
                comment.setUser(user);
                comment.setPost(post.get());
                newComment =  commentRepository.save(comment);
            }
        }

        return Optional.ofNullable(newComment);
    }

    @Override
    public Page<Comment> findAll(int page) {
        return commentRepository.findAllByOrderByDateCreatedDesc( PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public Page<Comment> findByPost(int page, Post post) {
        return commentRepository.findAllByPostOrderByDateCreatedDesc(post, PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public Page<Comment> findByUser(int page, User user) {
        return commentRepository.findAllByUserOrderByDateCreatedDesc(user, PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        return commentRepository.findById(id);
    }

    @Override
    public String deleteComment(UUID id) {
         commentRepository.deleteById(id);
         Optional<Comment> deletedComment =  findById(id);
         if(!deletedComment.isPresent()){
             return "success";
         }
         return null;
    }

    @Override
    public Optional<Comment> updateComment(UUID id, Comment comment) {
        Comment updatedComment = null;
        Optional<Comment> existingComment = commentRepository.findById(id);
        if(existingComment.isPresent()){
            updatedComment = existingComment.get();
            updatedComment.setBody(comment.getBody());
            updatedComment =  commentRepository.save(updatedComment);
        }
        return  Optional.ofNullable(updatedComment);
    }
}
