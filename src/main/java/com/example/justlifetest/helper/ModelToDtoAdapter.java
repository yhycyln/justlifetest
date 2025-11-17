package com.example.justlifetest.helper;

import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.dto.VehicleDto;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ModelToDtoAdapter {

    public BookingDto mapBookingToDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .timeSlotDto(mapTimestampToTimeSlotDto(booking.getStartDate(), booking.getEndDate()))
                .cleanerDtoList(booking.getCleaners().stream().map(this::mapCleanerToDto).toList())
                .build();
    }

    public TimeSlotDto mapTimestampToTimeSlotDto(Timestamp startTimestamp, Timestamp endTimestamp) {
        return TimeSlotDto.builder()
                .date(DateTimeUtil.timestampToStringDate(startTimestamp))
                .startTime(DateTimeUtil.timestampToStringDateHour(startTimestamp).substring(0,5))
                .endTime(DateTimeUtil.timestampToStringDateHour(endTimestamp).substring(0,5))
                .build();
    }

    public CleanerDto mapCleanerToDto(Cleaner cleaner) {
        return CleanerDto.builder()
                .id(cleaner.getId())
                .name(cleaner.getName())
                .surname(cleaner.getSurname())
                .vehicleId(cleaner.getVehicle().getId())
                .build();
    }

    public VehicleDto mapVehicleToDto(Vehicle vehicle) {
        return VehicleDto.builder()
                .id(vehicle.getId())
                .cleaners(vehicle.getCleaners().stream().map(this::mapCleanerToDto).toList())
                .name(vehicle.getName())
                .build();
    }
}