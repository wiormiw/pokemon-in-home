package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.Objects;

@Entity
public class User extends PanacheEntity {
    public String username;
    public String password;
    @ManyToMany
    @JoinTable(
            name = "user_pokemon",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "pokemon_id")
    )
    public List<Pokemon> pokemon;
    @ManyToMany
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    public List<User> friends;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof User user)) return false;

        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(pokemon, user.pokemon) && Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(pokemon);
        result = 31 * result + Objects.hashCode(friends);
        return result;
    }
}
