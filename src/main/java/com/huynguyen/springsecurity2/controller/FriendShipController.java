package com.huynguyen.springsecurity2.controller;

import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.service.FriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public ResponseEntity<String> acceptFriendRequest(@RequestParam("requestId") Long requestId) {
        FriendShip friendship = friendShipService.acceptFriendRequest(requestId);
        if (friendship != null) {
            return ResponseEntity.ok("Friend request accepted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to accept friend request.");
        }
    }

    @PostMapping("/cancelFriend")
    @ResponseBody
    public ResponseEntity<String> rejectFriendRequest(@RequestParam("userId1") Long userId1,
                                                      @RequestParam("userId2") Long userId2) {
        FriendShip friendship = friendShipService.cancelFriendRequest(userId1, userId2);
        if (friendship != null) {
            return ResponseEntity.ok("Friend request rejected successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to reject friend request.");
        }
    }
}
