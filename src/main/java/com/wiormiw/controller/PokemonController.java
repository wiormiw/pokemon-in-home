package com.wiormiw.controller;

import com.wiormiw.dto.PokemonDTO;
import com.wiormiw.service.PokemonService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/api/pokemon")
public class PokemonController {
    @Inject
    PokemonService pokemonService;

    @GET
    @Path("/pokedex")
    public List<PokemonDTO> getPokedex() {
        return pokemonService.getAllPokemon();
    }

    @POST
    @Path("/catch")
    @Authenticated
    public Response catchRandomPokemon(@Context SecurityContext ctx) {
        Long userId = Long.valueOf(ctx.getUserPrincipal().getName());
        PokemonDTO caught = pokemonService.catchRandomPokemon(userId);
        return Response.ok(caught).build();
    }
}
