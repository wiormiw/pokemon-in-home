package com.wiormiw.repository;

import com.wiormiw.entity.UserPokemon;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserPokemonRepository extends PanacheEntityBase {
    public void save(UserPokemon userPokemon) {
        persist(userPokemon);
    }
    public void deleteToSwap(Long userId, Long pokemonId) {
        delete("userId = ?1 and pokemonId = ?2", userId, pokemonId);
    }
}
