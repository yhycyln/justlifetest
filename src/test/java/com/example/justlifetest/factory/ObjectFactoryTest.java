package com.example.justlifetest.factory;

import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.AvailableVehicleDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.util.DateTimeUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ObjectFactoryTest {

    @Test
    void testCreateBooking() {
        TimeSlotDto timeSlotDto = TimeSlotDto.builder()
                .date("2023-10-01")
                .startTime("09:00")
                .endTime("11:00")
                .build();
        Cleaner cleaner = new Cleaner();
        cleaner.setId(1L);
        List<Cleaner> cleaners = List.of(cleaner);

        Booking booking = ObjectFactory.createBooking(timeSlotDto, cleaners);

        assertNotNull(booking);
        assertEquals(DateTimeUtil.stringToTimestamp("2023-10-01 09:00:00"), booking.getStartDate());
        assertEquals(DateTimeUtil.stringToTimestamp("2023-10-01 11:00:00"), booking.getEndDate());
        assertEquals(Set.copyOf(cleaners), booking.getCleaners());
        assertNotNull(booking.getId());
        assertNotNull(booking.getCreateTime());
    }

    @Test
    void testCreateTimeSlotDto() {
        TimeSlotDto timeSlotDto = ObjectFactory.createTimeSlotDto("2023-10-01", "09:00", "11:00");

        assertNotNull(timeSlotDto);
        assertEquals("2023-10-01", timeSlotDto.getDate());
        assertEquals("09:00", timeSlotDto.getStartTime());
        assertEquals("11:00", timeSlotDto.getEndTime());
    }

    @Test
    void testCreateAvailabilityDto() {
        AvailableVehicleDto availableVehicleDto = AvailableVehicleDto.builder()
                .vehicleId(101L)
                .cleanerWithTimeSlots(Map.of())
                .build();
        List<AvailableVehicleDto> availableVehicleDtoList = List.of(availableVehicleDto);

        AvailabilityDto availabilityDto = ObjectFactory.createAvailabilityDto(availableVehicleDtoList);

        assertNotNull(availabilityDto);
        assertEquals(availableVehicleDtoList, availabilityDto.getAvailableVehicleDtoList());
    }

    @Test
    void testCreateAvailableVehicleDto() {
        CleanerDto cleanerDto = CleanerDto.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .vehicleId(101L)
                .build();
        TimeSlotDto timeSlotDto = TimeSlotDto.builder()
                .date("2023-10-01")
                .startTime("09:00")
                .endTime("11:00")
                .build();
        Map<CleanerDto, List<TimeSlotDto>> cleanerWithTimeSlots = Map.of(cleanerDto, List.of(timeSlotDto));

        AvailableVehicleDto availableVehicleDto = ObjectFactory.createAvailableVehicleDto(101L, cleanerWithTimeSlots);

        assertNotNull(availableVehicleDto);
        assertEquals(101L, availableVehicleDto.getVehicleId());
        assertEquals(cleanerWithTimeSlots, availableVehicleDto.getCleanerWithTimeSlots());
    }

    @Test
    void testCreateVehicle() {
        Vehicle vehicle = ObjectFactory.createVehicle(101L);

        assertNotNull(vehicle);
        assertEquals(101L, vehicle.getId());
        assertEquals("Vehicle-101", vehicle.getName());
    }

    @Test
    void testCreateCleaner() {
        Vehicle vehicle = ObjectFactory.createVehicle(101L);
        Cleaner cleaner = ObjectFactory.createCleaner(1L, vehicle);

        assertNotNull(cleaner);
        assertEquals(1L, cleaner.getId());
        assertEquals("Cleaner-1", cleaner.getName());
        assertEquals(vehicle, cleaner.getVehicle());
    }
}