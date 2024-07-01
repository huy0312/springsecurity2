package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private boolean resetSuccess = false;

    /**
     * Displays the reset password form.
     *
     * @param model the model to which the new User object is added
     * @return the name of the view for the reset password page
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(Model model) {
        model.addAttribute("user", new User());
        return "reset-password";
    }

    /**
     * Handles the submission of the reset password form.
     *
     * @param userId             the ID of the user whose password is being reset
     * @param newPassword        the new password entered by the user
     * @param confirmPassword    the confirmation of the new password entered by the user
     * @param model              the model to which error messages are added if necessary
     * @param redirectAttributes attributes for a redirect scenario
     * @return a redirect to the login page if successful, otherwise reloads the reset password page with error messages
     */
    @PostMapping("/reset-password-confirm")
    public String confirmResetPassword(@RequestParam("userId") Long userId,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("confirmPassword") String confirmPassword,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {

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

        redirectAttributes.addFlashAttribute("resetSuccess", true);
        return "redirect:/login-delayed";
    }

    /**
     * Displays a delayed login page after a successful password reset.
     *
     * @return the name of the view for the delayed login page
     */
    @GetMapping("/login-delayed")
    public String loginDelayed() {
        return "login-delayed";
    }
}
