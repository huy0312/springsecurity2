package com.huynguyen.springsecurity2.controller;


import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.service.CustomUserDetails;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto) {
        return "register";
    }

    @PostMapping("/registration")
    public String saveUser(@ModelAttribute("user") UserDto userDto, Model model,@RequestParam("avatarFile") MultipartFile file) {
        if(!file.isEmpty()) {
            String avatarPath = saveAvatarFile(file);
            userDto.setAvatar(avatarPath);
        }
        model.addAttribute("message", "Registered Successfully");
        userService.save(userDto);
        return "register";
    }
    private String saveAvatarFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String directoryPath = new File("D:/uploads/avatar").getAbsolutePath();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }
        String filePath = directoryPath + File.separator + fileName;
        File dest = new File(filePath);
        try {
            file.transferTo(dest); // Lưu tệp
            System.out.println("Saved avatar file to: " + filePath); // Thêm lệnh log
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/uploads/avatar/" + fileName; // Trả về đường dẫn để sử dụng trong HTML
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user-page")
    public String userPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
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

    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.findAll(page,size);
        model.addAttribute("userPage", userPage);
        return "user-management";
    }

    @GetMapping("/formUpdate/{id}")
    public String formUpdate(@PathVariable("id")long id, Model model, RedirectAttributes ra) {
        try {
            User user = userService.get(id);
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
            model.addAttribute("user", userDto);
            model.addAttribute("pageTitle", "Update Successfully");
            return "user-form";
        }catch (UsernameNotFoundException e) {
            ra.addFlashAttribute("message", "User not found");
            return "redirect:/user-page";
        }

    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDto userDto, @RequestParam("avatarFile") MultipartFile file) {
        User existingUser = userService.findById(userDto.getId());
        if (existingUser != null) {
            if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
                userDto.setPassword(existingUser.getPassword());
            } else {
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
        }
        userService.save(userDto); // Lưu user với avatar mới
        return "redirect:/user-page";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") long Id) {
        userService.deleteById(Id);
        return "redirect:/list";
    }
}