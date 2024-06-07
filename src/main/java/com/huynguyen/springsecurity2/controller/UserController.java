package com.huynguyen.springsecurity2.controller;


import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

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
        String directoryPath = "D:/uploads/avatars";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String filePath = directoryPath+ fileName;
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
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
    public String profile(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "user-profile";
    }

    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<User> user = userService.findAll();
        model.addAttribute("user", user);
        return "user-management";
    }

    @GetMapping("/formUpdate")
    public String formUpdate(@RequestParam("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("user") UserDto userDto) {
        //save the employee
        userService.save(userDto);
        return "redirect:/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") long Id) {
        userService.deleteById(Id);
        return "redirect:/list";
    }
}