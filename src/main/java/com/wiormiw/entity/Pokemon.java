package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Pokemon extends PanacheEntity {
    public String name;
    public String description;
    public BigDecimal catchRate;
    public String characteristic;
    @ManyToMany
    @JoinTable(
            name = "pokemon_types",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "types")
    )
    public List<Type> types;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Pokemon pokemon)) return false;

        return Objects.equals(name, pokemon.name) && Objects.equals(description, pokemon.description) && Objects.equals(catchRate, pokemon.catchRate) && Objects.equals(characteristic, pokemon.characteristic) && Objects.equals(types, pokemon.types);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + Objects.hashCode(catchRate);
        result = 31 * result + Objects.hashCode(characteristic);
        result = 31 * result + Objects.hashCode(types);
        return result;
    }
}
