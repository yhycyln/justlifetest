package com.example.justlifetest.api.controller;

import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.api.response.ResponseOfGet;
import com.example.justlifetest.api.response.ResponseOfGetList;
import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.VehicleDto;
import com.example.justlifetest.service.interfaces.BookingCommandService;
import com.example.justlifetest.service.interfaces.BookingQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookingApiControllerTest {

    private BookingCommandService bookingCommandService;
    private BookingQueryService bookingQueryService;
    private BookingApiController bookingApiController;

    @BeforeEach
    void setUp() {
        bookingCommandService = mock(BookingCommandService.class);
        bookingQueryService = mock(BookingQueryService.class);
        bookingApiController = new BookingApiController(bookingCommandService, bookingQueryService);
    }

    @Test
    void testGetHello() {
        String response = bookingApiController.getHello();
        assertEquals("Hello, JustLife World!", response);
    }

    @Test
    void testGenerateVehiclesAndCleaners() {
        bookingApiController.generateVehiclesAndCleaners();
        verify(bookingCommandService, times(1)).generateVehiclesAndCleaners();
    }

    @Test
    void testCreateBooking() {
        BookingRequestDto requestDto = new BookingRequestDto();
        BookingDto expectedResponse = new BookingDto();
        when(bookingCommandService.createBooking(requestDto)).thenReturn(expectedResponse);

        BookingDto response = bookingApiController.createBooking(requestDto);

        assertEquals(expectedResponse, response);
        verify(bookingCommandService, times(1)).createBooking(requestDto);
    }

    @Test
    void testUpdateBooking() {
        BookingUpdateRequestDto requestDto = new BookingUpdateRequestDto();
        BookingDto expectedResponse = new BookingDto();
        when(bookingCommandService.updateBooking(requestDto)).thenReturn(expectedResponse);

        BookingDto response = bookingApiController.updateBooking(requestDto);

        assertEquals(expectedResponse, response);
        verify(bookingCommandService, times(1)).updateBooking(requestDto);
    }

    @Test
    void testGetCleanerAvailabilityWithDate() {
        String date = "2023-10-01";
        AvailabilityDto availabilityDto = new AvailabilityDto();
        when(bookingQueryService.getCleanerAvailabilityWithDate(date)).thenReturn(availabilityDto);

        ResponseOfGet<AvailabilityDto> response = bookingApiController.getCleanerAvailabilityWithDate(date);

        assertEquals(availabilityDto, response.getResult());
        verify(bookingQueryService, times(1)).getCleanerAvailabilityWithDate(date);
    }

    @Test
    void testGetCleanerAvailabilityWithTimeSlot() {
        String date = "2023-10-01";
        String startTime = "09:00";
        String endTime = "11:00";
        AvailabilityDto availabilityDto = new AvailabilityDto();
        when(bookingQueryService.getCleanerAvailabilityWithTimeSlot(date, startTime, endTime)).thenReturn(availabilityDto);

        ResponseOfGet<AvailabilityDto> response = bookingApiController.getCleanerAvailabilityWithTimeSlot(date, startTime, endTime);

        assertEquals(availabilityDto, response.getResult());
        verify(bookingQueryService, times(1)).getCleanerAvailabilityWithTimeSlot(date, startTime, endTime);
    }

    @Test
    void testGetBookings() {
        List<String> bookingIdList = List.of("1", "2", "3");
        List<BookingDto> bookingDtoList = List.of(new BookingDto());
        when(bookingQueryService.getBookings(bookingIdList)).thenReturn(bookingDtoList);

        ResponseOfGetList<BookingDto> response = bookingApiController.getBookings(bookingIdList);

        assertEquals(bookingDtoList, response.getList());
        verify(bookingQueryService, times(1)).getBookings(bookingIdList);
    }

    @Test
    void testGetAllBookings() {
        List<BookingDto> bookingDtoList = List.of(new BookingDto());
        when(bookingQueryService.getAllBookings()).thenReturn(bookingDtoList);

        ResponseOfGetList<BookingDto> response = bookingApiController.getBookings();

        assertEquals(bookingDtoList, response.getList());
        verify(bookingQueryService, times(1)).getAllBookings();
    }

    @Test
    void testGetAllVehicles() {
        List<VehicleDto> vehicleDtoList = List.of(new VehicleDto());
        when(bookingQueryService.getAllVehicles()).thenReturn(vehicleDtoList);

        ResponseOfGetList<VehicleDto> response = bookingApiController.getAllVehicles();

        assertEquals(vehicleDtoList, response.getList());
        verify(bookingQueryService, times(1)).getAllVehicles();
    }

    @Test
    void testGetAllCleaners() {
        List<CleanerDto> cleanerDtoList = List.of(new CleanerDto());
        when(bookingQueryService.getAllCleaners()).thenReturn(cleanerDtoList);

        ResponseOfGetList<CleanerDto> response = bookingApiController.getAllCleaners();

        assertEquals(cleanerDtoList, response.getList());
        verify(bookingQueryService, times(1)).getAllCleaners();
    }
}