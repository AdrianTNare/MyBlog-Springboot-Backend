package com.sir_ad.myBlog_backend.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sir_ad.myBlog_backend.model.Post;
import com.sir_ad.myBlog_backend.service.PostService;
import com.sir_ad.myBlog_backend.utiliy.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

//update to include information of logged in user when making nad updating comments.
@RequestMapping("posts")
@RestController
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping("/create")
    public MappingJacksonValue addPost(@NotNull @RequestBody Post post){
        Optional<Post> newPost = postService.addPost(post);

        return getMappingJacksonValue(newPost);
    }

    @GetMapping("/all")
    public MappingJacksonValue findAll(@RequestParam(defaultValue = "0") int page){
        Pager pager = new Pager(postService.findAll(page));

        return getMappingJacksonValue(pager);
    }

    @GetMapping("/user")
    public MappingJacksonValue findAllByUser(@Valid @NotNull @RequestParam String username, @RequestParam(defaultValue = "0") int page){
        Pager pager = new Pager(postService.findAllByUser(username, page));
        return getMappingJacksonValue(pager);
    }

    @GetMapping("/id")
    public MappingJacksonValue findById(@RequestParam UUID id){
        Optional<Post> post = postService.findById(id);

        return getMappingJacksonValue(post);
    }

    @GetMapping("/title")
    public MappingJacksonValue findByTitle(@RequestParam String title){
        Optional<Post> post = postService.findByTitle(title);

        return getMappingJacksonValue(post);
    }

    @DeleteMapping("/delete")
    public MappingJacksonValue deletePost(@RequestParam UUID id){
        return getMappingJacksonValue( postService.deletePost(id));
    }

    @PutMapping("/update")
    public MappingJacksonValue updatePost(@RequestParam UUID id, @Valid @NotNull @RequestBody Post post){
            Optional<Post> updatedPost =  postService.updatePost(id, post);
            return getMappingJacksonValue(updatedPost);

    }

    <T> MappingJacksonValue getMappingJacksonValue(T value){
        SimpleBeanPropertyFilter postUserFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("username","name", "lastname");
        SimpleBeanPropertyFilter postFilter = SimpleBeanPropertyFilter
                .serializeAllExcept("comments");
        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider()
                .addFilter("postUserFilter",postUserFilter)
                .addFilter("postFilter",postFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        mappingJacksonValue.setFilters(simpleFilterProvider);

        return mappingJacksonValue;
    }
}
