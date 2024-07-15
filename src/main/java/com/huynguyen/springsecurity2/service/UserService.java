package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    User save(UserDto userDto);

    List<User> findAll();

    User findById(Long id);

    User get(Long id);

    void deleteById(Long id);

    Page<User> findAll(int page, int size );

    Page<User> searchUser(String keyword, Pageable pageable);

    long countRecords();

    void updateUserStatus(Long id, String status);

    void updateUserStatusByCode(Long id, boolean status);

    List<User> searchByEmailOrUsername(String keyword);

    List<User> getAllUsers();

    User findByEmail(String email);

    boolean resetPassword(Long id, String password);

    UserDto findUserByVerificationCode(String verificationCode);

    void updateUserRole(Long id, String role);

    UserDto getUser(User user);

    void updateUser(User user);


}

