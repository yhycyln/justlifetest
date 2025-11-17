package com.example.justlifetest.model;

import com.example.justlifetest.model.base.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Cleaner extends BaseEntity {

    @Nonnull
    private String name;

    @Nonnull
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="vehicle_id", nullable=false)
    private Vehicle vehicle;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Cleaner_Booking",
            joinColumns = { @JoinColumn(name = "cleaner_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "booking_id", referencedColumnName = "id") }
    )
    private Set<Booking> bookings;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cleaner cleaner = (Cleaner) o;
        return Objects.equals(name, cleaner.name) && Objects.equals(surname, cleaner.surname) && Objects.equals(vehicle, cleaner.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, vehicle);
    }
}
