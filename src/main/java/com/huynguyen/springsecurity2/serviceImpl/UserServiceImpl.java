package com.huynguyen.springsecurity2.serviceImpl;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import com.huynguyen.springsecurity2.service.EmailService;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

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

            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
                    user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                }
            }
            if (userDto.getAvatar() != null && !userDto.getAvatar().isEmpty()) {
                user.setAvatar(userDto.getAvatar());
            }
        } else {
            user = new User(userDto.getEmail(),
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getFullname(),
                    userDto.getRole(),
                    userDto.getPhone(),
                    userDto.getEnable(),
                    userDto.getAvatar(),
                    userDto.getCountry(),
                    userDto.getCity(), emailService.generateVerificationCode());
        }
        userRepository.save(user);
        emailService.sendEmail(user, "Email Verification");
        return user;
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
    public Page<User> searchUser(String keyword, Pageable pageable) {
        return this.userRepository.searchUser(keyword, pageable);
    }

    @Override
    public long countRecords() {
        return userRepository.count();
    }

    @Override
    public void updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEnable(status.equals("active"));
            userRepository.save(user);
        }
    }

    @Override
    public void updateUserStatusByCode(Long id, boolean status) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnable(true);
        userRepository.save(user);
    }

    @Override
    public List<User> searchByEmailOrUsername(String keyword) {
        return userRepository.findByEmailContainingOrFullnameContaining(keyword, keyword);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean resetPassword(Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDto findUserByVerificationCode(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);
        return user != null ? getUser(user) : null;
    }

    @Override
    public void updateUserRole(Long id, String role) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setRole(role);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with id " + id);
        }
    }

    @Override
    public UserDto getUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setFullname(user.getFullname());
        userDto.setRole(user.getRole());
        userDto.setPhone(user.getPhone());
        userDto.setEnable(user.getEnable());
        userDto.setAvatar(user.getAvatar());
        userDto.setCity(user.getCity());
        userDto.setCountry(user.getCountry());
        return userDto;
    }

}
