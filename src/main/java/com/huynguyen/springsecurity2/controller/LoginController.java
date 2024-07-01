package com.huynguyen.springsecurity2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Handles GET requests to the /login endpoint.
     *
     * @return the name of the view for the login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
