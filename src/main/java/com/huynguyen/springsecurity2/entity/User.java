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
    private long phone;

    @Column(name = "enable")
    private short enable;

    @Column(name="avatar")
    private String avatar;


    public User(String email, String password, String fullname, String role, long phone,short enable,String avatar) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.phone = phone;
        this.enable = enable;
        this.avatar = avatar;

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

    @Transient
    public String getAvatarImagePath(){
        if(avatar ==null || id ==null){
            return null;
        }
        return "/avatar/"+avatar+".jpg";
    }
}
