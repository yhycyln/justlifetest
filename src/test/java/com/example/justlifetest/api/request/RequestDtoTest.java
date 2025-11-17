package com.example.justlifetest.api.request;

import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestDtoTest {

    @Test
    void testBookingUpdateRequestDto() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);
        List<CleanerDto> cleanerList = List.of(cleanerDto);

        BookingUpdateRequestDto dto = new BookingUpdateRequestDto(1L, timeSlotDto, cleanerList);

        assertNotNull(dto);
        assertEquals(1L, dto.getBookingId());
        assertEquals(timeSlotDto, dto.getTimeSlotDto());
        assertEquals(cleanerList, dto.getCleanerList());
    }

    @Test
    void testBookingUpdateRequestDtoBuilder() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");
        CleanerDto cleanerDto = new CleanerDto(1L, "John", "Doe", 101L);
        List<CleanerDto> cleanerList = List.of(cleanerDto);

        BookingUpdateRequestDto dto = BookingUpdateRequestDto.builder()
                .bookingId(1L)
                .timeSlotDto(timeSlotDto)
                .cleanerList(cleanerList)
                .build();

        assertNotNull(dto);
        assertEquals(1L, dto.getBookingId());
        assertEquals(timeSlotDto, dto.getTimeSlotDto());
        assertEquals(cleanerList, dto.getCleanerList());
    }

    @Test
    void testBookingRequestDto() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");

        BookingRequestDto dto = new BookingRequestDto(timeSlotDto, 101L, 2);

        assertNotNull(dto);
        assertEquals(timeSlotDto, dto.getTimeSlotDto());
        assertEquals(101L, dto.getVehicleId());
        assertEquals(2, dto.getCleanerCount());
    }

    @Test
    void testBookingRequestDtoBuilder() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-01", "09:00", "11:00");

        BookingRequestDto dto = BookingRequestDto.builder()
                .timeSlotDto(timeSlotDto)
                .vehicleId(101L)
                .cleanerCount(2)
                .build();

        assertNotNull(dto);
        assertEquals(timeSlotDto, dto.getTimeSlotDto());
        assertEquals(101L, dto.getVehicleId());
        assertEquals(2, dto.getCleanerCount());
    }
}
