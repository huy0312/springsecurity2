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

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegistrationService registrationService;

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
                    userDto.getCity(), userDto.getVerificationCode());
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
    public Page<User> searchUser(String keyword,Pageable pageable) {
        return this.userRepository.searchUser(keyword,pageable);
    }

    @Override
    public long countRecords() {
        return userRepository.count();
    }

    @Override
    public void sendVerificationCode(String email, String verificationCode) {

        String subject = "Verification Code";
        String text = "Your verification code is: " + verificationCode;
        registrationService.sendEmail(email, subject, text);

        User user = userRepository.findByEmail(email);
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
    }

    @Override
    public boolean confirmVerificationCode(String email, String verificationCode) {
        User user = userRepository.findByEmail(email);
        if(verificationCode.equals(user.getVerificationCode())) {
            user.setEnable(true);
            userRepository.save(user);
            return true;
        }
        return false;
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
    public List<User> searchByEmailOrUsername(String keyword) {
        return userRepository.findByEmailContainingOrFullnameContaining(keyword,keyword);
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
        if(user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }


}
