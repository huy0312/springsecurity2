package com.huynguyen.springsecurity2.service;

import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.User;

import java.util.List;

public interface FriendShipService {

    public FriendShip sendFriendRequest(Long userid1, Long userid2);

    public FriendShip acceptFriendRequest(Long friendShipId);

    public List<FriendShip> getFriendRequests(Long userid);

    public User getUserById(Long userid);

}
