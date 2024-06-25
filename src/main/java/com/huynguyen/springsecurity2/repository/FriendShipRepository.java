package com.huynguyen.springsecurity2.repository;

import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.enums.FriendShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendShipRepository  extends JpaRepository<FriendShip, Long> {

    List<FriendShip> findByUser1IdAndStatus(Long user1Id, FriendShipStatus status);

    List<FriendShip> findByUser2IdAndStatus(Long user2Id, FriendShipStatus status);

    FriendShip findByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    List<FriendShip> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);

    List<FriendShip> findByStatus(FriendShipStatus status);


    List<FriendShip> findByIdOrStatus(Long userId, FriendShipStatus status);

    List<FriendShip> findByUser1IdAndUser2IdAndStatus(Long user1Id, Long user2Id, FriendShipStatus status);

}
