package com.example.justlifetest.model;

import com.example.justlifetest.model.base.BaseEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Booking extends BaseEntity {

    @Nonnull
    private Timestamp startDate;

    @Nonnull
    private Timestamp endDate;

    @ManyToMany(mappedBy = "bookings")
    private Set<Cleaner> cleaners;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(startDate, booking.startDate) && Objects.equals(endDate, booking.endDate) && Objects.equals(cleaners, booking.cleaners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate);
    }
}
