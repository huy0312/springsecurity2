package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.entity.enums.FriendShipStatus;
import com.huynguyen.springsecurity2.repository.FriendShipRepository;
import com.huynguyen.springsecurity2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class FriendShipServiceImpl implements FriendShipService{

    @Autowired
    private FriendShipRepository friendShipRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public FriendShip sendFriendRequest(Long userId1, Long userId2) {
        FriendShip existingFriendship = friendShipRepository.findByUser1IdAndUser2Id(userId1, userId2);
        if (existingFriendship == null) {
            User user1 = userRepository.findById(userId1).orElse(null);
            User user2 = userRepository.findById(userId2).orElse(null);
            FriendShipStatus status = FriendShipStatus.PENDING;
            if (user1 != null && user2 != null) {
                FriendShip newFriendship = new FriendShip(user1, user2, status);
                return friendShipRepository.save(newFriendship);
            }
        }
        return null;
    }

    @Override
    public FriendShip acceptFriendRequest(Long friendShipId) {
        Optional<FriendShip> friendshipOpt = friendShipRepository.findById(friendShipId);
        if (friendshipOpt.isPresent()) {
            FriendShip existingFriendship = friendshipOpt.get();
            existingFriendship.setStatus(FriendShipStatus.ACCEPTED);
            return friendShipRepository.save(existingFriendship);
        }
        throw new RuntimeException("Friendship not found");
    }

    @Override
    @Transactional
    public FriendShip cancelFriendRequest(Long userId1, Long userId2) {
        FriendShip friendship = friendShipRepository.findByUser1IdAndUser2Id(userId1, userId2);
        if (friendship != null && friendship.getStatus() == FriendShipStatus.PENDING) {
            friendship.setStatus(FriendShipStatus.REJECTED);
            friendship.setCreatedAt(LocalDateTime.now());
            friendShipRepository.save(friendship);
            return friendship;
        }
        return null;
    }

    @Override
    public boolean canSendFriendRequest(User userid1, User userid2) {
        Optional<FriendShip> lastRejectedRequest = friendShipRepository.findTopByUser1AndUser2AndStatusOrderByCreatedAtDesc(userid1, userid2, "REJECTED");
        if (!lastRejectedRequest.isPresent()) {
            return true;
        }
        LocalDateTime twentyMinutesAgo = LocalDateTime.now().minusMinutes(20);
        return lastRejectedRequest.get().getCreatedAt().isBefore(twentyMinutesAgo);
    }

    @Override
    public List<FriendShip> getFriendRequests(Long userid) {
        return friendShipRepository.findByUser2IdAndStatus(userid, FriendShipStatus.PENDING);
    }

    @Override
    public User getUserById(Long userid) {
        return userRepository.findById(userid).orElse(null);
    }

}
