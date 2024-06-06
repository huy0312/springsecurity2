package com.huynguyen.springsecurity2.dto;


public class UserDto {

    private String email;

    private String password;

    private String fullname;

    private String role;

    public UserDto() {
    }

    public UserDto(String email, String fullname, String password, String role) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
