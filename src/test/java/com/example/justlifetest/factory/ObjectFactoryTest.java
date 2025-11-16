package com.example.justlifetest.factory;

import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.AvailableVehicleDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.util.DateTimeUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ObjectFactoryTest {

    @Test
    void testCreateBooking() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        Cleaner cleaner = mock(Cleaner.class);
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
        AvailableVehicleDto availableVehicleDto = mock(AvailableVehicleDto.class);
        List<AvailableVehicleDto> availableVehicleDtoList = List.of(availableVehicleDto);

        AvailabilityDto availabilityDto = ObjectFactory.createAvailabilityDto(availableVehicleDtoList);

        assertNotNull(availabilityDto);
        assertEquals(availableVehicleDtoList, availabilityDto.getAvailableVehicleDtoList());
    }

    @Test
    void testCreateAvailableVehicleDto() {
        CleanerDto cleanerDto = new CleanerDto("John", "Doe", 101L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        Map<CleanerDto, List<TimeSlotDto>> cleanerWithTimeSlots = Map.of(cleanerDto, List.of(timeSlotDto));

        AvailableVehicleDto availableVehicleDto = ObjectFactory.createAvailableVehicleDto(101L, cleanerWithTimeSlots);

        assertNotNull(availableVehicleDto);
        assertEquals(101L, availableVehicleDto.getVehicleId());
        assertEquals(cleanerWithTimeSlots, availableVehicleDto.getCleanerWithTimeSlots());
    }
}