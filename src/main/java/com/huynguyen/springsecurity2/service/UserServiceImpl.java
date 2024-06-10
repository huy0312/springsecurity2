package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        User user;
        if (userDto.getId() != null && userRepository.existsById(userDto.getId())) {
            user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user.setEmail(userDto.getEmail());
            user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Lưu ý: Bạn có thể muốn giữ nguyên mật khẩu nếu không có thay đổi
            user.setFullname(userDto.getFullname());
            user.setRole(userDto.getRole());
            user.setPhone(userDto.getPhone());
            user.setEnable(userDto.getEnable());
            user.setAvatar(userDto.getAvatar());
        } else {
            user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getFullname(), userDto.getRole(), userDto.getPhone(), userDto.getEnable(), userDto.getAvatar());
        }
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
    public User get(Long id) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new RuntimeException("Did not find any user");
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
