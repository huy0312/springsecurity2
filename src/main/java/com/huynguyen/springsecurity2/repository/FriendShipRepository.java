package com.huynguyen.springsecurity2.repository;

import com.huynguyen.springsecurity2.entity.FriendShip;
import com.huynguyen.springsecurity2.entity.enums.FriendShipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendShipRepository  extends JpaRepository<FriendShip, Long> {

    List<FriendShip> findByIdOrStatus(Long userId, FriendShipStatus status);

}
