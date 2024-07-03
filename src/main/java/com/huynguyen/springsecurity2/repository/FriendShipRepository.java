package com.huynguyen.springsecurity2.repository;

import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.User;
import com.huynguyen.springsecurity2.entity.enums.FriendShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendShipRepository  extends JpaRepository<FriendShip, Long> {

    List<FriendShip> findByUser1IdAndStatus(Long user1Id, FriendShipStatus status);

    List<FriendShip> findByUser2IdAndStatus(Long user2Id, FriendShipStatus status);

    FriendShip findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    Optional<FriendShip> findTopByUser1AndUser2AndStatusOrderByCreatedAtDesc(User user1, User user2, String status);

}
