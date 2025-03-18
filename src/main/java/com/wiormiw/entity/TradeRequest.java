package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class TradeRequest extends PanacheEntity {
    public Long requesterId;
    public Long targetId;
    public Long requesterPokemonId;
    public Long targetPokemonId;
    public String status; // PENDING, ACCEPTED, REJECTED, CANCELLED
}
