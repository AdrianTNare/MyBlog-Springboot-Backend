package com.sir_ad.myBlog_backend.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.PostRepository;
import com.sir_ad.myBlog_backend.repository.UserRepository;
import com.sir_ad.myBlog_backend.service.PostService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    private int subtractPageByOne(int page){
        return (page < 1) ? 0 : page - 1 ;
    }

    @Override
    public Optional<Post> addPost(Post post) {
        Post newPost = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Claims claims = (Claims) authentication.getPrincipal();
            String currentUserName = claims.getSubject();
            User optionalUser = userRepository.findByUsername(currentUserName);
            if(optionalUser != null){
                post.setUser(optionalUser);
                newPost =  postRepository.save(post);
            }
        }
        return Optional.ofNullable(newPost);
    }

    @Override
    public Page<Post> findAll(int page) {
        return postRepository.findAllByOrderByDateCreatedDesc( PageRequest.of(subtractPageByOne(page),5 ));
    }

//    might be better to move the logic for retrieving the user object back to the controller
//    where it could possibly be used against the principal , the same could be done
//    for the other methods within this class
    @Override
    public Page<Post> findAllByUser( String username, int page) {
        User user = userRepository.findByUsername(username);

        return postRepository.findAllByUserOrderByDateCreatedDesc(user, PageRequest.of(subtractPageByOne(page), 5));
    }

    @Override
    public Optional<Post> findById(UUID id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public String deletePost(UUID id) {
        postRepository.deleteById(id);
        Optional<Post> post = findById(id);
        if(post.isPresent()){
            return "fail";
        }
        return null;
    }

    @Override
    public Optional<Post> updatePost(UUID id, Post post) {
        Post updatedPost = null;
        Optional<Post> originalPost = postRepository.findById(id);
        if(originalPost.isPresent()){
            updatedPost = originalPost.get();
            updatedPost.setBody(post.getBody());
            updatedPost =  postRepository.save(updatedPost);
        }
        return  Optional.ofNullable(updatedPost);
    }
}
