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

@Controller
public class FriendShipController {

    @Autowired
    FriendShipService friendShipService;

    /**
     * Sends a friend request from one user to another.
     *
     * @param userId1 the ID of the user sending the friend request
     * @param userId2 the ID of the user receiving the friend request
     * @param model the model to add attributes to
     * @return a redirect to the user page with a success message
     */
    @PostMapping("/send")
    public String sendFriendRequest(@RequestParam("userId1") Long userId1,
                                    @RequestParam("userId2") Long userId2,
                                    Model model) {
        friendShipService.sendFriendRequest(userId1, userId2);
        model.addAttribute("message", "Friend request sent successfully!");
        return "redirect:/user-page";
    }

    /**
     * Accepts a friend request.
     *
     * @param requestId the ID of the friend request to accept
     * @return a response entity with a success or failure message
     */
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

    /**
     * Rejects a friend request.
     *
     * @param userId1 the ID of the user who sent the friend request
     * @param userId2 the ID of the user who received the friend request
     * @return a response entity with a success or failure message
     */
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
