package com.huynguyen.springsecurity2.repository;

import com.huynguyen.springsecurity2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends  JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.fullname LIKE %:keyword% OR u.email LIKE %:keyword%")
    Page<User> searchUser(String keyword, Pageable pageable);

    List<User> findByEmailContainingOrFullnameContaining(String email, String fullname);

    Page<User> findByEnable(boolean enable, Pageable pageable);
}
