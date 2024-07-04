package com.huynguyen.springsecurity2.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 404) {
            return "error/404";
        } else if (statusCode == 500) {
            return "error/500";
        } else {
            return "error/error";
        }
    }

    public String getErrorPath() {
        return "/error";
    }

}
