package com.wiormiw.repository;

import com.wiormiw.entity.Friends;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FriendsRepository extends PanacheEntityBase {
    public boolean areFriends(Long userId, Long friendId) {
        return find("userId = ?1 and friendId = ?2", userId, friendId).firstResult() != null;
    }

    public void addFriend(Long userId, Long friendId) {
        Friends friendship = new Friends();
        friendship.userId = userId;
        friendship.friendId = friendId;
        persist(friendship);
    }
}
