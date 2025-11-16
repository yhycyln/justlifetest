package com.example.justlifetest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private List<CleanerDto> cleanerDtoList;
    private TimeSlotDto timeSlotDto;
}