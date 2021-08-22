package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@ApiModel(value = "User Api Model Documentation",description = "Model")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @ApiModelProperty(value = "Unique id field of user object")
    private Long id;

    @Column(name="username", unique = true, nullable = false)
    @ApiModelProperty(value = "UserName field of user object")
    private String username;

    @Column(name="email", unique = true)
    @ApiModelProperty(value = "Email field of user object")
    private String email;

    @Column(name="password", nullable = false)
    @ApiModelProperty("Password field of user object")
    private String password;

    @Column(name = "address", nullable = true, columnDefinition = "TEXT")
    @ApiModelProperty("Address field of user object")
    private String address;

    @Column(name = "business_address",nullable = true,columnDefinition = "TEXT")
    @ApiModelProperty("BusinessAddress field of user object")
    private String business_address;

    @Column(name = "business_name",nullable = true)
    @ApiModelProperty("BusinessName field of user object")
    private String business_name;

    @Column(name = "enabled")
    @ApiModelProperty("Enabled account field of user object")
    private boolean enabled=false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ApiModelProperty("Role field of user object")
    private Set<RoleEntity> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusiness_address() {
        return business_address;
    }

    public void setBusiness_address(String business_address) {
        this.business_address = business_address;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }
}
