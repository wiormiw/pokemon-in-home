package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class User extends PanacheEntity {
    public String username;
    public String password;
}
