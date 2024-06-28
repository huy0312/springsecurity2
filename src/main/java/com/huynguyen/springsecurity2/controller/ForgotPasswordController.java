package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import com.huynguyen.springsecurity2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/reset-password-request")
    public String resetPasswordRequest(@RequestParam("email") String email, HttpServletRequest request) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return "redirect:/forgot-password?error=User not found";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/reset-password";
    }
}

