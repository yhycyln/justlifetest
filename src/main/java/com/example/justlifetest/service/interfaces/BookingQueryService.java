package com.example.justlifetest.service.interfaces;


import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.BookingDto;

import java.util.List;

public interface BookingQueryService {
    AvailabilityDto getCleanerAvailabilityWithDate(String date);

    AvailabilityDto getCleanerAvailabilityWithTimeSlot(String date, String startTime, String endTime);
    List<BookingDto> getBookings(List<String> bookingIdList);
}
