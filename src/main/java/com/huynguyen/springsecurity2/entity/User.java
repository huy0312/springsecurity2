package com.huynguyen.springsecurity2.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "role")
    private String role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name="verificationCode")
    private String verificationCode;


    public User(String email, String password, String fullname, String role, String phone, boolean enable, String avatar, String country, String city, String verificationCode) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.phone = phone;
        this.enable = enable;
        this.avatar = avatar;
        this.country = country;
        this.city = city;
        this.verificationCode = verificationCode;

    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Transient
    public String getAvatarImagePath() {
        if (avatar == null) {
            return null;
        }
        return "/avatar/" + avatar;
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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
