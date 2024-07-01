package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.repository.UserRepository;
import com.huynguyen.springsecurity2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Handles a password reset request by email.
     *
     * @param email the email of the user requesting a password reset
     * @param request the HTTP request object
     * @param ra redirect attributes for passing messages to the redirect target
     * @return a redirect to the reset password page or the forgot password page with an error
     */
    @PostMapping("/reset-password-request")
    public String resetPasswordRequest(@RequestParam("email") String email, HttpServletRequest request, RedirectAttributes ra) {
        User user = userService.findByEmail(email);
        if (user == null) {
            request.getSession().setAttribute("error", "User not found");
            return "redirect:/forgot-password?error=User not found";
        }
        request.getSession().setAttribute("user", user);
        return "redirect:/reset-password";
    }

    /**
     * Displays the forgot password page.
     *
     * @param request the HTTP request object
     * @param model the model to add attributes to
     * @return the name of the forgot password view
     */
    @GetMapping("/forgot-password")
    public String forgotPassword(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String error = (String) session.getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error);
            session.removeAttribute("error");
        }
        return "forgot-password";
    }
}
