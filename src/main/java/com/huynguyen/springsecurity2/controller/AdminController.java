package com.huynguyen.springsecurity2.controller;


import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/admin-page")
    public String adminPage(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        return "admin";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortField, @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "asc") String sortOrder, @RequestParam(defaultValue = "") String status) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage;
        if (keyword == null || keyword.isEmpty()) {
            if (status.isEmpty()) {
                userPage = userRepository.findAll(pageable);
            } else {
                boolean isActive = status.equals("active");
                userPage = userRepository.findByEnable(isActive, pageable);
            }
        } else {
            userPage = userService.searchUser(keyword, pageable);
        }

        long totalRecords = userService.countRecords();
        model.addAttribute("totalRecords", totalRecords);
        model.addAttribute("userPage", userPage);
        return "user-management";
    }

    @PostMapping("/update-user-status")
    public ResponseEntity<?> updateUserStatus(@RequestParam Long id, @RequestParam boolean status) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                user.setEnable(status);
                userRepository.save(user);
                return ResponseEntity.ok().body(Collections.singletonMap("success", true));
            } else {
                return ResponseEntity.badRequest().body(Collections.singletonMap("success", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("success", false));
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") long Id) {
        userService.deleteById(Id);
        return "redirect:/list";
    }
}
