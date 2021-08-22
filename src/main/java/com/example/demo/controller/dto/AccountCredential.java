package com.example.demo.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountCredential {

    @NotNull
    @NotBlank
    private String username;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String password;


    private String address;
    private String business_address;
    private String business_name;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
