package com.huynguyen.springsecurity2.entity;

import com.huynguyen.springsecurity2.entity.enums.FriendShipStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class FriendShip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private User user2;

    @Enumerated(EnumType.STRING)
    private FriendShipStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();

    public FriendShip() {
    }

    public FriendShip(User user1, User user2, FriendShipStatus status, LocalDateTime createdAt) {
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
        this.createdAt = createdAt;
    }

    public FriendShipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendShipStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
