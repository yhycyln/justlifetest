package com.example.justlifetest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailableVehicleDto {
    private long vehicleId;
    private Map<CleanerDto, List<TimeSlotDto>> cleanerWithTimeSlots;
}
