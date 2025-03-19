package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "friends")
public class Friends extends PanacheEntityBase {
    @Id
    @Column(name = "user_id")
    public Long userId;
    @Id
    @Column(name = "friend_id")
    public Long friendId;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Friends friends)) return false;

        return Objects.equals(userId, friends.userId) && Objects.equals(friendId, friends.friendId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(friendId);
        return result;
    }
}
