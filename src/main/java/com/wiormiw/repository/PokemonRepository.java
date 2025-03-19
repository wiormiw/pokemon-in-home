package com.wiormiw.repository;

import com.wiormiw.entity.Pokemon;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PokemonRepository implements PanacheRepository<Pokemon> {
}
