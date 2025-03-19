package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class TradeRequest extends PanacheEntity {
    public Long requesterId;
    public Long targetId;
    public Long requesterPokemonId;
    public Long targetPokemonId;
    public String status; // PENDING, ACCEPTED, REJECTED, CANCELLED

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof TradeRequest that)) return false;

        return Objects.equals(requesterId, that.requesterId) && Objects.equals(targetId, that.targetId) && Objects.equals(requesterPokemonId, that.requesterPokemonId) && Objects.equals(targetPokemonId, that.targetPokemonId) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(requesterId);
        result = 31 * result + Objects.hashCode(targetId);
        result = 31 * result + Objects.hashCode(requesterPokemonId);
        result = 31 * result + Objects.hashCode(targetPokemonId);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }
}
