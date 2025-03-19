package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "user_pokemon")
public class UserPokemon extends PanacheEntityBase {
    @Id
    @Column(name = "user_id")
    public Long userId;
    @Id
    @Column(name = "pokemon_id")
    public Long pokemonId;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof UserPokemon that)) return false;

        return Objects.equals(userId, that.userId) && Objects.equals(pokemonId, that.pokemonId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(userId);
        result = 31 * result + Objects.hashCode(pokemonId);
        return result;
    }
}
