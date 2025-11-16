package com.example.justlifetest.model;

import com.example.justlifetest.model.base.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Where(clause = BaseEntity.SOFT_DELETE_CLAUSE)
public class Vehicle extends BaseEntity {

    @Nonnull
    private String name;

    @OneToMany(mappedBy="vehicle")
    private Set<Cleaner> cleaners;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Cleaner_Booking",
            joinColumns = { @JoinColumn(name = "cleaner_id") },
            inverseJoinColumns = { @JoinColumn(name = "booking_id") }
    )
    private Set<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(name, vehicle.name) && Objects.equals(cleaners, vehicle.cleaners) && Objects.equals(bookings, vehicle.bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, cleaners, bookings);
    }
}
