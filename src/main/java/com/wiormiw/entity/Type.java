package com.wiormiw.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class Type extends PanacheEntity {
    public String name;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Type type)) return false;

        return Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
