package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.dto.UserDto;
import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.User;

import java.util.List;

public interface FriendShipService {

    public FriendShip sendFriendRequest(Long userid1, Long userid2);

    public FriendShip acceptFriendRequest(Long friendShipId);

    public List<FriendShip> getFriendRequests(Long userid);

    public User getUserById(Long userid);

    public FriendShip cancelFriendRequest(Long userId1, Long userId2);

    boolean canSendFriendRequest(User userid1, User userid2);

    public List<FriendShip> getFriends(Long userid);


}
