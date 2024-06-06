package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;

import java.util.List;

public interface UserService {


    User save(UserDto userDto);

    public List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);

}
