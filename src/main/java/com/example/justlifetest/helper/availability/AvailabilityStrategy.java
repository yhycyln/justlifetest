package com.example.justlifetest.helper.availability;

import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.model.Cleaner;

import java.util.List;

public interface AvailabilityStrategy {
    List<Cleaner> getAvailableCleaners(List<Cleaner> cleanerList, TimeSlotDto timeSlotDto);
    boolean isCleanersAvailable(List<CleanerDto> cleanerList, TimeSlotDto timeSlotDto);
}