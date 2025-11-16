package com.example.justlifetest.api.request;

import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingUpdateRequestDto {
    private Long bookingId;
    private TimeSlotDto timeSlotDto;
    private List<CleanerDto> cleanerList;
}