package com.example.justlifetest.service.interfaces;


import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.dto.BookingDto;

public interface BookingCommandService {
    BookingDto createBooking(BookingRequestDto requestDto);

    BookingDto updateBooking(BookingUpdateRequestDto requestDto);
}
