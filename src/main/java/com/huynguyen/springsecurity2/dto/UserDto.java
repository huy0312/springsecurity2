package com.huynguyen.springsecurity2.dto;


import java.beans.Transient;

public class UserDto {

    private String email;

    private String password;

    private String fullname;

    private String role;

    private long phone;

    private short enable;

    private String avatar;

    public UserDto() {
    }

    public UserDto(String email, String fullname, String password, String role, long phone,short enable,String avatar) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.enable = enable;
        this.avatar = avatar;
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public short getEnable() {
        return enable;
    }

    public void setEnable(short enable) {
        this.enable = enable;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



}
