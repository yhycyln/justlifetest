package com.example.justlifetest.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testCleanerModel() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName("Vehicle 1");

        Booking booking = new Booking();
        booking.setStartDate(new Timestamp(System.currentTimeMillis()));
        booking.setEndDate(new Timestamp(System.currentTimeMillis() + 3600000));

        Cleaner cleaner = new Cleaner();
        cleaner.setName("John");
        cleaner.setSurname("Doe");
        cleaner.setVehicle(vehicle);
        cleaner.setBookings(Set.of(booking));

        assertEquals("John", cleaner.getName());
        assertEquals("Doe", cleaner.getSurname());
        assertEquals(vehicle, cleaner.getVehicle());
        assertTrue(cleaner.getBookings().contains(booking));
    }

    @Test
    void testVehicleModel() {
        Cleaner cleaner = new Cleaner();
        cleaner.setName("John");
        cleaner.setSurname("Doe");

        Booking booking = new Booking();
        booking.setStartDate(new Timestamp(System.currentTimeMillis()));
        booking.setEndDate(new Timestamp(System.currentTimeMillis() + 3600000));

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Vehicle 1");
        vehicle.setCleaners(Set.of(cleaner));
        vehicle.setBookings(Set.of(booking));

        assertEquals("Vehicle 1", vehicle.getName());
        assertTrue(vehicle.getCleaners().contains(cleaner));
        assertTrue(vehicle.getBookings().contains(booking));
    }

    @Test
    void testBookingModel() {
        Cleaner cleaner = new Cleaner();
        cleaner.setName("John");
        cleaner.setSurname("Doe");

        Booking booking = new Booking();
        booking.setStartDate(new Timestamp(System.currentTimeMillis()));
        booking.setEndDate(new Timestamp(System.currentTimeMillis() + 3600000));
        booking.setCleaners(Set.of(cleaner));

        assertNotNull(booking.getStartDate());
        assertNotNull(booking.getEndDate());
        assertTrue(booking.getCleaners().contains(cleaner));
    }

    @Test
    void testEqualsAndHashCode() {
        Cleaner cleaner1 = new Cleaner();
        cleaner1.setName("John");
        cleaner1.setSurname("Doe");

        Cleaner cleaner2 = new Cleaner();
        cleaner2.setName("John");
        cleaner2.setSurname("Doe");

        assertEquals(cleaner1, cleaner2);
        assertEquals(cleaner1.hashCode(), cleaner2.hashCode());

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setName("Vehicle 1");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setName("Vehicle 1");

        assertEquals(vehicle1, vehicle2);
        assertEquals(vehicle1.hashCode(), vehicle2.hashCode());

        Booking booking1 = new Booking();
        booking1.setStartDate(new Timestamp(System.currentTimeMillis()));
        booking1.setEndDate(new Timestamp(System.currentTimeMillis() + 3600000));

        Booking booking2 = new Booking();
        booking2.setStartDate(booking1.getStartDate());
        booking2.setEndDate(booking1.getEndDate());

        assertEquals(booking1, booking2);
        assertEquals(booking1.hashCode(), booking2.hashCode());
    }
}