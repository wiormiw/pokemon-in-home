package com.wiormiw.service;

import com.wiormiw.dto.PokemonDTO;
import com.wiormiw.entity.Pokemon;
import com.wiormiw.entity.UserPokemon;
import com.wiormiw.exception.PokemonException;
import com.wiormiw.repository.PokemonRepository;
import com.wiormiw.repository.UserPokemonRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Random;

@ApplicationScoped
public class PokemonService {
    @Inject
    PokemonRepository pokemonRepository;
    @Inject
    UserPokemonRepository userPokemonRepository;

    public List<PokemonDTO> getAllPokemon() {
        return pokemonRepository.findAllPokemon().stream()
                .map(PokemonDTO::fromEntity)
                .toList();
    }

    public PokemonDTO catchRandomPokemon(Long userId) {
        List<Pokemon> wildPokemon = pokemonRepository.findAllPokemon();
        Pokemon randomPokemon = wildPokemon.get(new Random().nextInt(wildPokemon.size()));
        double roll = new Random().nextDouble() * 100;
        if (roll <= randomPokemon.catchRate.doubleValue()) {
            UserPokemon link = new UserPokemon();
            link.userId = userId;
            link.pokemonId = randomPokemon.id;
            userPokemonRepository.save(link);
            return PokemonDTO.fromEntity(randomPokemon);
        }
        throw new PokemonException("Pokémon escaped!");
    }
}
