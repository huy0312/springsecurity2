package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.entity.enums.FriendShipStatus;
import com.huynguyen.springsecurity2.repository.FriendShipRepository;
import com.huynguyen.springsecurity2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FriendShipServiceImpl implements FriendShipService{

    @Autowired
    private FriendShipRepository friendShipRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public FriendShip sendFriendRequest(Long userid1, Long userid2) {
        Optional<User> user1 = userRepository.findById(userid1);
        Optional<User> user2 = userRepository.findById(userid2);

        if (user1.isPresent() && user2.isPresent()) {
            FriendShip friendship = new FriendShip();
            friendship.setUser1(user1.get());
            friendship.setUser2(user2.get());
            friendship.setStatus(FriendShipStatus.PENDING);
            return friendShipRepository.save(friendship);
        } else {
            throw new RuntimeException("Users not found");
        }
    }

    @Override
    public FriendShip acceptFriendRequest(Long friendShipId) {
        Optional<FriendShip> friendship = friendShipRepository.findById(friendShipId);
        if (friendship.isPresent()) {
            FriendShip existingFriendship = friendship.get();
            existingFriendship.setStatus(FriendShipStatus.ACCEPTED);
            return friendShipRepository.save(existingFriendship);
        } else {
            throw new RuntimeException("Friendship not found");
        }
    }

    @Override
    public List<FriendShip> getFriendRequests(Long userid) {
        return friendShipRepository.findByIdOrStatus(userid, FriendShipStatus.PENDING);
    }

    @Override
    public User getUserById(Long userid) {
        return userRepository.findById(userid).orElse(null);
    }
}
