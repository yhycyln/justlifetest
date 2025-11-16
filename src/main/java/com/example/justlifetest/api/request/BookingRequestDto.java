package com.example.justlifetest.api.request;

import com.example.justlifetest.dto.TimeSlotDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDto {
    private TimeSlotDto timeSlotDto;
    private long vehicleId;
    private int cleanerCount;
}