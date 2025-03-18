package com.wiormiw.repository;

import com.wiormiw.entity.Pokemon;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PokemonRepository extends PanacheEntityBase {
    public List<Pokemon> findAllPokemon() {
        return listAll();
    }
}
