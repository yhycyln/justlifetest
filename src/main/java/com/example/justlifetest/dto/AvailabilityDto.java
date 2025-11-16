package com.example.justlifetest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDto {
    private List<AvailableVehicleDto> availableVehicleDtoList;
}