package com.huynguyen.springsecurity2.dto;


public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String fullname;

    private String role;

    private long phone;

    private short enable;

    private String avatar;

    private String country;

    private String city;

    public UserDto() {
    }

    public UserDto(String email, String fullname, String password, String role, long phone, short enable, String avatar, String country, String city) {
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.enable = enable;
        this.avatar = avatar;
        this.country = country;
        this.city = city;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
