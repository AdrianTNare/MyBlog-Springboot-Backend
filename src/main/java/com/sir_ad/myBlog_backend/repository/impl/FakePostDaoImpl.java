package com.sir_ad.myBlog_backend.repository.impl;


import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.repository.PostDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FakePostDaoImpl implements PostDao {

    private static List<Post> DB = new ArrayList<>();
    @Override
    public Optional<Post> addPost(UUID id, Post post) {
        DB.add(new Post(id, post.getTitle(), post.getBody(), post.getUser()));
        return findById(id);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postsToPage(DB, pageable);

    }

    @Override
    public Page<Post> findAllByUser(User user, Pageable pageable) {
        List<Post> userPosts = DB.stream()
                .filter(post -> post.getUser().equals(user))
                .collect(Collectors.toList());
        return postsToPage(userPosts, pageable);
    }

    @Override
    public Optional<Post> findById(UUID id) {
        return DB.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return DB.stream()
                .filter(post -> post.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public int deletePost(UUID id) {
        Optional<Post> optionalPost = findById(id);
        if(optionalPost.isEmpty()){
            return 0;
        }
        DB.remove(optionalPost.get());
        return 1;
    }

    @Override
    public Optional<Post> updatePost(UUID id, Post post) {

        return  findById(id)
                .map(item -> {
                    Post updatedPost = null;
                    int itemIndex = DB.indexOf(item);
                    if(itemIndex >=0 ) {
                        DB.set(itemIndex, new Post(id, post.getTitle(), post.getBody(), post.getUser()));
                        updatedPost = findById(id).orElse(null);
                    }
                    return updatedPost;
                });
    }

    Page<Post> postsToPage(List<Post> postList, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), postList.size());
        if(start > postList.size()){
            return new PageImpl<>(new ArrayList<>(), pageable, postList.size());
        }
        return new PageImpl<>(postList.subList(start, end), pageable, postList.size());
    }
}
