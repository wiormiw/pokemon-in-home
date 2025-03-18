package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_pokemon")
public class UserPokemon extends PanacheEntityBase {
    @Id
    @Column(name = "user_id")
    public Long userId;

    @Id
    @Column(name = "pokemon_id")
    public Long pokemonId;
}
