package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "friends")
public class Friends extends PanacheEntityBase {
    @Id
    @Column(name = "user_id")
    public Long userId;
    @Id
    @Column(name = "friend_id")
    public Long friendId;
}
