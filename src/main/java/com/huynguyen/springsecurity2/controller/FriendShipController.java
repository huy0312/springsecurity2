package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.service.FriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class FriendShipController {

    @Autowired
    FriendShipService friendShipService;

    @PostMapping("/send")
    public String sendFriendRequest(@RequestParam("userId1") Long userId1,
                                    @RequestParam("userId2") Long userId2,
                                    Model model) {
        friendShipService.sendFriendRequest(userId1, userId2);
        model.addAttribute("message", "Friend request sent successfully!");
        return "redirect:/user-page";
    }

    @PostMapping("/acceptFriend")
    public String acceptFriendRequest(@RequestParam("requestId") Long quesId, RedirectAttributes ra) {
        friendShipService.acceptFriendRequest(quesId);
        ra.addFlashAttribute("message", "Friend request accepted!");
        return "redirect:/user-page";
    }

    @PostMapping("/cancelFriend")
    public String cancelFriendRequest(@RequestParam("userId1") Long userid1, @RequestParam("userId2") Long userid2, RedirectAttributes ra) {
        friendShipService.cancelFriendRequest(userid1, userid2);
        ra.addFlashAttribute("message", "Friend request cancelled!");
        return "redirect:/user-page";
    }
}
