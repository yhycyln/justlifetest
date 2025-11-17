package com.example.justlifetest.repository;


import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends RepositoryBase<Booking> {
    List<Booking> findAllByCleanersAndStartDateBetween(Set<Cleaner> cleaners, Timestamp bookingDate, Timestamp nextBookingDate);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE (b.id IN :idList) " +
            "AND (b.deleted = false) " )
    List<Booking> getBookingsByIdList(@Param("idList") List<String> bookingIdList);

    Set<Booking> findAllByCleaners(List<Cleaner> cleaner);

    @Query("SELECT b FROM Booking AS b " +
            "WHERE (b.deleted = false) " )
    List<Booking> findAllBookings();
}
