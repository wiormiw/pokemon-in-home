package com.wiormiw.dto;

import com.wiormiw.entity.Pokemon;

import java.math.BigDecimal;
import java.util.List;

public record PokemonDTO(
        Long id,
        String name,
        List<String> types,
        String description,
        BigDecimal catchRate,
        String characteristic
) {
    public static PokemonDTO fromEntity(Pokemon pokemon) {
        return new PokemonDTO(
                pokemon.id,
                pokemon.name,
                pokemon.types,
                pokemon.description,
                pokemon.catchRate,
                pokemon.characteristic
        );
    }
}
