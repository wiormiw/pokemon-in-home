package com.wiormiw.repository;

import com.wiormiw.entity.UserPokemon;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserPokemonRepository implements PanacheRepository<UserPokemon> {
    public void save(UserPokemon userPokemon) {
        persist(userPokemon);
    }

    public void deleteToSwap(Long userId, Long pokemonId) {
        delete("userId = ?1 and pokemonId = ?2", userId, pokemonId);
    }

    public boolean isOwningPokemon(Long userId, Long pokemonId) {
        return find("userId = ?1 and pokemonId = ?2", userId, pokemonId)
                .firstResultOptional()
                .isPresent();
    }
}
