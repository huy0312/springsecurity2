package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/reset-password")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("user", new User());
        return "reset-password";
    }

    @PostMapping("/reset-password-confirm")
    public String confirmResetPassword(@RequestParam("userId") Long userId,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "reset-password";
        }
        String encodedPassword = passwordEncoder.encode(newPassword);

        boolean resetSuccess = userService.resetPassword(userId, encodedPassword);
        if (!resetSuccess) {
            model.addAttribute("error", "Failed to reset password");
            return "reset-password";
        }
        model.addAttribute("success", true);
        return "reset-password";
    }

}
