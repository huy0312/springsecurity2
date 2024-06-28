package com.huynguyen.springsecurity2.controller;


import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.Message;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import com.huynguyen.springsecurity2.service.CustomUserDetails;
import com.huynguyen.springsecurity2.service.FriendShipService;
import com.huynguyen.springsecurity2.service.MessageService;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private FriendShipService friendShipService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model, @RequestParam("avatarFile") MultipartFile file) {
        if (!file.isEmpty()) {
            String avatarPath = saveAvatarFile(file);
            userDto.setAvatar(avatarPath);
        }
        model.addAttribute("message", "Registered Successfully");
        userService.save(userDto);
        return "register";
    }

    private String saveAvatarFile(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID() + fileExtension;
        String directoryPath = new File("D:/uploads/avatar").getAbsolutePath();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = directoryPath + File.separator + newFileName;
        File dest = new File(filePath);
        try {
            String contentType = file.getContentType();
            assert contentType != null;
            if (!contentType.startsWith("image/")) {
                throw new IllegalArgumentException("Only image file are accepted");
            }
            file.transferTo(dest);
            System.out.println("Saved avatar file to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save avatar file", e);
        }
        return "/uploads/avatar/" + newFileName;
    }

    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User currentUser = userService.findByEmail(principal.getName());
        List<User> users = userService.getAllUsers();
        List<FriendShip> pendingRequests = friendShipService.getFriendRequests(currentUser.getId());
        List<FriendShip> friends = friendShipService.getFriends(currentUser.getId());
        int friendCount = friends.size();
        model.addAttribute("users", users);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", userDetails);
        model.addAttribute("friends", friends);
        model.addAttribute("friendCount", friendCount);
        return "user";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("avatarUrl", user.getAvatar());
        return "user-profile";
    }

    private static UserDto getUserDto(User user) {
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

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDto userDto,
                           @RequestParam("avatarFile") MultipartFile file) {
        User existingUser = userService.findById(userDto.getId());

        if (existingUser != null) {
            if (!file.isEmpty()) {
                String avatarPath = saveAvatarFile(file);
                userDto.setAvatar(avatarPath);
            } else {
                userDto.setAvatar(existingUser.getAvatar());
            }
        }
        userService.save(userDto);
        return "redirect:/profile";
    }

    @PostMapping("/addFriend/{userId1}/{userId2}")
    public String addFriend(@PathVariable Long userId1, @PathVariable Long userId2, Model model) {
        friendShipService.sendFriendRequest(userId1, userId2);
        model.addAttribute("message", "Friend request sent successfully!");
        return "redirect:/user-page";
    }
}