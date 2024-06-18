package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Oauth2Controller {

    @Autowired
    private UserService userService;

    @GetMapping("/oauth2/authorization/google")
    public String googleLogin() {
        return "redirect:/login/oauth2/code/google";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "redirect:/user-page";
    }

}
