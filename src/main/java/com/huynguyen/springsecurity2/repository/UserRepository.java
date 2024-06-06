package com.huynguyen.springsecurity2.repository;

import com.huynguyen.springsecurity2.entity.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends  JpaRepository<User,Long> {
    User findByEmail(String email);
}
