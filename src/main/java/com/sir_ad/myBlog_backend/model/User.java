package com.sir_ad.myBlog_backend.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

// class to be modified when the application gets more complex.

@JsonFilter("userFilter")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private  UUID id;

    @Column(name = "username", nullable = false, unique = true)
    @Length(min = 5, message = "Your unique username must be at least 5 characters long.")
    @NotEmpty(message = "Your unique username cannot be empty.")
    private String username;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Your name cannot be empty")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = " Please provide your last name")
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email.")
    private String email;

    @Column(name = "password", nullable = false)
    @Length(min = 6, message = "Your password must be at least 6 characters long")
    @NotEmpty(message = "Please provide your password.")
    private String password;

    @OneToMany(mappedBy = "user")
    private Collection<Post> posts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(UUID id, String username, String email, String name, String lastname) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
