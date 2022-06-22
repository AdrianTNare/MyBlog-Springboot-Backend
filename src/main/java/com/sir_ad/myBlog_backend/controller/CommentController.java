package com.sir_ad.myBlog_backend.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sir_ad.myBlog_backend.model.Comment;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.service.CommentService;
import com.sir_ad.myBlog_backend.service.PostService;
import com.sir_ad.myBlog_backend.service.UserService;
import com.sir_ad.myBlog_backend.utiliy.CommentPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//update to include information of logged-in user when making nad updating comments.
@RequestMapping("comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;


    @Autowired
    public CommentController(CommentService commentService, PostService postService, UserService userService){
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

//    refactor the endpoint to simply return the new comment without having to use the id
//    since the user doesn't have any knowledge about the id at this point
    @PostMapping("/create")
    public MappingJacksonValue addComment(@NotNull @RequestBody Comment comment){
        Optional<Comment> newComment = commentService.addComment(comment);

        return getMappingJacksonValue(newComment);

    }

    @GetMapping("/all")
    public MappingJacksonValue findAll(@RequestParam(defaultValue = "0")int page){
        CommentPager commentPager = new CommentPager(commentService.findAll(page));

        return getMappingJacksonValue(commentPager);
    }

    @GetMapping("/post")
    public MappingJacksonValue findByPost(@RequestParam(defaultValue = "0")int page, @Valid @NotNull @RequestParam UUID id){
        Optional<Post> post = postService.findById(id);
        CommentPager commentPager = new CommentPager(commentService.findByPost(page, post.get()));

        return getMappingJacksonValue(commentPager);
    }

    @GetMapping("/user")
    public MappingJacksonValue findByUser(@RequestParam(defaultValue = "0")int page, @Valid @NotNull @RequestParam String username){
        User user = userService.findByUsername(username);
        CommentPager commentPager = new CommentPager(commentService.findByUser(page, user));

        return getMappingJacksonValue(commentPager);
    }

    @GetMapping("/id")
    public MappingJacksonValue findById( @Valid @NotNull @RequestParam UUID id){
        Optional<Comment> comment =  commentService.findById(id);

        return getMappingJacksonValue(comment);
    }

    @DeleteMapping("/delete")
    public MappingJacksonValue deleteComment(@Valid @NotNull @RequestParam UUID id){
        return getMappingJacksonValue(commentService.deleteComment(id));
    }

    @PutMapping("/update")
    public MappingJacksonValue updateComment(@RequestParam UUID id, @Valid @NotNull @RequestBody Comment comment){
        Optional<Comment> updatedComment = commentService.updateComment(id, comment);

        return getMappingJacksonValue(updatedComment);
    }

    <T> MappingJacksonValue getMappingJacksonValue(T value){
        SimpleBeanPropertyFilter commentUserFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("username","name","lastname");

        SimpleBeanPropertyFilter commentPostFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id");

        SimpleFilterProvider provider = new SimpleFilterProvider()
                .addFilter("commentUserFilter", commentUserFilter)
                .addFilter("commentPostFilter", commentPostFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        mappingJacksonValue.setFilters(provider);
        return mappingJacksonValue;
    }
}
