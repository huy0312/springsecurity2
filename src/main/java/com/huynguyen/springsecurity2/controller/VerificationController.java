package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify")
    public String verify() {
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyUser(@RequestParam("code") String code, Model model) {
        UserDto userDto = userService.findUserByVerificationCode(code);
        if (userDto != null) {
            userService.updateUserStatusByCode(userDto.getId(), true); // Enable the user
            return "redirect:/success";
        } else {
            model.addAttribute("error", "Invalid verification code.");
            return "verify";
        }
    }

    @GetMapping("/success")
    public String successPage() {
        return "success";
    }
}
