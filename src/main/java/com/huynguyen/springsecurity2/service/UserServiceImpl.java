package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(UserDto userDto) {
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getFullname(), userDto.getRole());
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        User user = null;
        if (result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("Did not find any user");
        }
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
