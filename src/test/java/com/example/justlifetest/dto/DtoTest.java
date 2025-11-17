package com.example.justlifetest.dto;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DtoTest {

    @Test
    void testCleanerDto() {
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);

        assertNotNull(cleanerDto);
        assertEquals(1L, cleanerDto.getId());
        assertEquals("John", cleanerDto.getName());
        assertEquals("Doe", cleanerDto.getSurname());
        assertEquals(101L, cleanerDto.getVehicleId());
    }

    @Test
    void testTimeSlotDto() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");

        assertNotNull(timeSlotDto);
        assertEquals("2023-10-01", timeSlotDto.getDate());
        assertEquals("09:00", timeSlotDto.getStartTime());
        assertEquals("11:00", timeSlotDto.getEndTime());
    }

    @Test
    void testAvailableVehicleDto() {
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        Map<CleanerDto, List<TimeSlotDto>> cleanerWithTimeSlots = Map.of(cleanerDto, List.of(timeSlotDto));

        AvailableVehicleDto availableVehicleDto = new AvailableVehicleDto(101L, cleanerWithTimeSlots);

        assertNotNull(availableVehicleDto);
        assertEquals(101L, availableVehicleDto.getVehicleId());
        assertEquals(cleanerWithTimeSlots, availableVehicleDto.getCleanerWithTimeSlots());
    }

    @Test
    void testBookingDto() {
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        List<CleanerDto> cleanerDtoList = List.of(cleanerDto);

        BookingDto bookingDto = new BookingDto(1L, cleanerDtoList, timeSlotDto);

        assertNotNull(bookingDto);
        assertEquals(1L, bookingDto.getId());
        assertEquals(cleanerDtoList, bookingDto.getCleanerDtoList());
        assertEquals(timeSlotDto, bookingDto.getTimeSlotDto());
    }

    @Test
    void testAvailabilityDto() {
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        Map<CleanerDto, List<TimeSlotDto>> cleanerWithTimeSlots = Map.of(cleanerDto, List.of(timeSlotDto));
        AvailableVehicleDto availableVehicleDto = new AvailableVehicleDto(101L, cleanerWithTimeSlots);
        List<AvailableVehicleDto> availableVehicleDtoList = List.of(availableVehicleDto);

        AvailabilityDto availabilityDto = new AvailabilityDto(availableVehicleDtoList);

        assertNotNull(availabilityDto);
        assertEquals(availableVehicleDtoList, availabilityDto.getAvailableVehicleDtoList());
    }

    @Test
    void testVehicleDto() {
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);
        List<CleanerDto> cleaners = List.of(cleanerDto);

        VehicleDto vehicleDto = new VehicleDto(101L, "Van", cleaners);

        assertNotNull(vehicleDto);
        assertEquals(101L, vehicleDto.getId());
        assertEquals("Van", vehicleDto.getName());
        assertEquals(cleaners, vehicleDto.getCleaners());
    }
}