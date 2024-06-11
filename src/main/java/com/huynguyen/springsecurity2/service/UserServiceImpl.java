package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            user = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            user.setEmail(userDto.getEmail());
            user.setFullname(userDto.getFullname());
            user.setRole(userDto.getRole());
            user.setPhone(userDto.getPhone());
            user.setEnable(userDto.getEnable());
            user.setCity(userDto.getCity());
            user.setCountry(userDto.getCountry());

            // Chỉ mã hóa mật khẩu nếu nó đã được thay đổi
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty() &&
                    !passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }

            // Cập nhật avatar nếu có
            if (userDto.getAvatar() != null && !userDto.getAvatar().isEmpty()) {
                user.setAvatar(userDto.getAvatar());
            }
        } else {
            // Tạo mới user với tất cả các thuộc tính từ userDto
            user = new User(userDto.getEmail(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getFullname(),
                    userDto.getRole(),
                    userDto.getPhone(),
                    userDto.getEnable(),
                    userDto.getAvatar(),
                    userDto.getCountry(),
                    userDto.getCity());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        User user;
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

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> search(String keyword) {
        List<User> userList = userRepository.search(keyword);
        if (userList == null) {
            return Collections.emptyList(); // Hoặc bất kỳ danh sách trống nào khác
        }
        return userList;
    }

    @Override
    public long countRecords() {
        return userRepository.count();
    }
}
