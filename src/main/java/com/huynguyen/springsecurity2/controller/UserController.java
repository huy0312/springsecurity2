package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.service.CustomUserDetails;
import com.huynguyen.springsecurity2.service.EmailService;
import com.huynguyen.springsecurity2.service.FriendShipService;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FriendShipService friendShipService;

    @Autowired
    private EmailService emailService;


    /**
     * Displays the registration page.
     *
     * @param userDto the user data transfer object
     * @return the name of the view for registration
     */
    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    /**
     * Handles the user registration.
     *
     * @param userDto the user data transfer object
     * @param model   the model to which attributes are added
     * @param file    the avatar file uploaded by the user
     * @return the name of the view for registration
     */
    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model, @RequestParam("avatarFile") MultipartFile file) {
        if (userDto.getRole() == null || userDto.getRole().isEmpty()) {
            userDto.setRole("USER");
        }
        userDto.setEnable(false);

        String verificationCode = emailService.generateVerificationCode();
        userDto.setVerificationCode(verificationCode);
        if (!file.isEmpty()) {
            String avatarPath = saveAvatarFile(file);
            userDto.setAvatar(avatarPath);
        }
        userService.save(userDto);
        model.addAttribute("message", "Verification email sent to " + userDto.getEmail());
        return "redirect:/verify";
    }

    /**
     * Saves the avatar file to the specified directory.
     *
     * @param file the avatar file uploaded by the user
     * @return the path of the saved avatar file
     */
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
                throw new IllegalArgumentException("Only image files are accepted");
            }
            file.transferTo(dest);
            System.out.println("Saved avatar file to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save avatar file", e);
        }
        return "/uploads/avatar/" + newFileName;
    }

    /**
     * Displays the user page with user details, friends, and friend requests.
     *
     * @param model     the model to which attributes are added
     * @param principal the authenticated user's principal
     * @return the name of the view for the user page
     */
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

    /**
     * Displays the user profile page.
     *
     * @param model          the model to which attributes are added
     * @param authentication the authenticated user's authentication
     * @return the name of the view for the user profile page
     */
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("avatarUrl", user.getAvatar());
        return "user-profile";
    }

    /**
     * Saves the user details, including the avatar file if provided.
     *
     * @param userDto the user data transfer object
     * @param file    the avatar file uploaded by the user
     * @return a redirect to the profile page
     */
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
    /**
     * Sends a friend request from one user to another.
     *
     * @param userId1 the ID of the user sending the friend request
     * @param userId2 the ID of the user receiving the friend request
     * @param model   the model to which attributes are added
     * @return a redirect to the user page
     */
    @PostMapping("/addFriend/{userId1}/{userId2}")
    public String addFriend(@PathVariable Long userId1, @PathVariable Long userId2, Model model) {
        friendShipService.sendFriendRequest(userId1, userId2);
        model.addAttribute("message", "Friend request sent successfully!");
        return "redirect:/user-page";
    }

    @PostMapping("/saveCroppedAvatar")
    @ResponseBody
    public ResponseEntity<String> saveCroppedAvatar(@RequestParam("croppedAvatar") MultipartFile file,
                                                    Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                User currentUser = userService.findById(userDetails.getId());

                // Lưu file ảnh vào thư mục và cập nhật đường dẫn ảnh trong cơ sở dữ liệu
                String fileName = UUID.randomUUID().toString() + ".jpg";
                String filePath = "D:/uploads/avatar/" + fileName;
                File dest = new File(filePath);
                file.transferTo(dest);

                // Cập nhật thông tin ảnh của người dùng trong cơ sở dữ liệu
                currentUser.setAvatar("/uploads/avatar/" + fileName);
                userService.updateUser(currentUser);

                // Trả về URL của ảnh mới
                return ResponseEntity.ok("/uploads/avatar/" + fileName);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving avatar!");
        }
    }








}
