package com.sir_ad.myBlog_backend.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sir_ad.myBlog_backend.model.User;
import com.sir_ad.myBlog_backend.service.UserService;
import com.sir_ad.myBlog_backend.utiliy.Pager;
import com.sir_ad.myBlog_backend.utiliy.UserPager;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

//ideally, in any of the endpoints, we shouldn't be getting the id directly from the user
// , rather , we should be getting the id from authenticating their token/cookie first, query
// their id from the token middleware and add that id to the "request params", this will then
// be made available to the endpint in question
// consider adding a "log in route for the user"

@RequestMapping("users")
@RestController
public class UserController {

//    have to choose between @requestparam , @pathvariable, @requestbody for methods
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

//    the service will have to be altered to first check if the user exists in the arrayList
    @PostMapping("/register")
    void insertUser(@Valid @NotNull @RequestBody User user){
         userService.insertUser(user);

    }

//    makes sense to use @pathvariable for the page number here
//    real users might not have to user this endpoint.
    @GetMapping("/all{page}")
    MappingJacksonValue findAll(@RequestParam(name="page" ,defaultValue = "0" ) int page){
        UserPager pager = new UserPager(userService.findAll(page));

        return getMappingJacksonValue(pager);
    }

    @GetMapping("/find/username{username}")
    MappingJacksonValue findByUsername(@RequestParam(name="username") String username){
        User user = userService.findByUsername(username);
        return getMappingJacksonValue(user);
    }

//    might wanna put the id as a path variable for the posts
    @GetMapping("/find/id{id}")
    MappingJacksonValue findById(@RequestParam UUID id){
        Optional<User> user =  userService.findById(id);

        return getMappingJacksonValue(user);
    }

//    currently facing an internal server error when attemping to delete.
    @DeleteMapping("/delete")
    MappingJacksonValue deleteUser(@RequestParam("username") String username){
        return getMappingJacksonValue( userService.deleteByUsername(username));
    }

    @PutMapping("/update")
    MappingJacksonValue updateUser(@RequestParam String username,@Valid @NotNull @RequestBody User user){
        Optional<User> updatedUser = userService.updateUser(username, user);

        return getMappingJacksonValue(updatedUser);

    }

    <T> MappingJacksonValue getMappingJacksonValue(T value) {
        SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("username","name","lastname");

        SimpleFilterProvider provider = new SimpleFilterProvider()
                .addFilter("userFilter", userFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);
        mappingJacksonValue.setFilters(provider);
        return mappingJacksonValue;
    }
}
