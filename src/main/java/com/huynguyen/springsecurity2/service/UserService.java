package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {


    User save(UserDto userDto);

    public List<User> findAll();

    User findById(Long id);

    public User get(Long id);

    void deleteById(Long id);

    Page<User> findAll(int page, int size );

    Page<User> searchUser(String keyword, Pageable pageable);

    public long countRecords();

    public void sendVerificationCode(String email, String verificationCode);

    public boolean confirmVerificationCode(String email, String verificationCode);


    void updateUserStatus(Long id, String status);

    public List<User> searchByEmailOrUsername(String keyword);
}
