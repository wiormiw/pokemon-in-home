package com.wiormiw.repository;

import com.wiormiw.entity.TradeRequest;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TradeRequestRepository extends PanacheEntityBase {
    public TradeRequest findById(Long id) {
        return find("id", id).firstResult();
    }

    public List<TradeRequest> findConflictingTrades(Long userId, Long pokemonId) {
        return find("targetId = ?1 and targetPokemonId = ?2 and status = 'PENDING'", userId, pokemonId).list();
    }
}
