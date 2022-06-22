package com.sir_ad.myBlog_backend.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private  UUID id ;

    @Column(name = "role_name", unique = true)
    private  String roleName;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private Collection<User> users;

    public Role() {
    }

    public Role(UUID id, String roleName){
        this.id = id;
        this.roleName = roleName;
    }

    public UUID getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
