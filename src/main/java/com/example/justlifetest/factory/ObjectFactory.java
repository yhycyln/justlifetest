package com.example.justlifetest.factory;

import com.example.justlifetest.constants.BusinessConstants;
import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.AvailableVehicleDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.util.DateTimeUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ObjectFactory {

    private ObjectFactory() {
        // Private constructor to prevent instantiation
    }

    public static Booking createBooking(TimeSlotDto timeSlotDto, List<Cleaner> cleaners) {
        Booking booking = new Booking();
        booking.setStartDate(DateTimeUtil.stringToTimestamp(
                timeSlotDto.getDate().concat(" ").concat(timeSlotDto.getStartTime()).concat(BusinessConstants.DEFAULT_SECONDS_FOR_DATE)));
        booking.setEndDate(DateTimeUtil.stringToTimestamp(
                timeSlotDto.getDate().concat(" ").concat(timeSlotDto.getEndTime()).concat(BusinessConstants.DEFAULT_SECONDS_FOR_DATE)));
        booking.setCleaners(Set.copyOf(cleaners));
        booking.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        booking.setCreateTime(DateTimeUtil.getCurrentTimestamp());
        return booking;
    }

    public static TimeSlotDto createTimeSlotDto(String date, String startTime, String endTime) {
        return TimeSlotDto.builder()
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public static AvailabilityDto createAvailabilityDto(List<AvailableVehicleDto> availableVehicleDtoList) {
        return AvailabilityDto.builder()
                .availableVehicleDtoList(availableVehicleDtoList)
                .build();
    }

    public static AvailableVehicleDto createAvailableVehicleDto(long vehicleId, Map<CleanerDto, List<TimeSlotDto>> cleanerWithTimeSlots) {
        return AvailableVehicleDto.builder()
                .vehicleId(vehicleId)
                .cleanerWithTimeSlots(cleanerWithTimeSlots)
                .build();
    }

    public static Vehicle createVehicle(long id) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        vehicle.setName("Vehicle-" + id);
        return vehicle;
    }

    public static Cleaner createCleaner(long id, Vehicle vehicle) {
        Cleaner cleaner = new Cleaner();
        cleaner.setId(id);
        cleaner.setName("Cleaner-" + id);
        cleaner.setVehicle(vehicle);
        return cleaner;
    }
}