package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.math.BigDecimal;
import java.util.List;

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
            inverseJoinColumns = @JoinColumn(name = "type")
    )
    public List<String> types;
}
