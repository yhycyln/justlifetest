package com.example.justlifetest.helper;

import com.example.justlifetest.constants.BusinessConstants;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.helper.availability.AvailabilityStrategy;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.util.DateTimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckAvailabilityHelperTest {

    private CheckAvailabilityHelper checkAvailabilityHelper;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private AvailabilityStrategy availabilityStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkAvailabilityHelper = new CheckAvailabilityHelper(bookingRepository, availabilityStrategy);
    }

    @Test
    void testGetAvailableCleaners() {
        List<Cleaner> cleanerList = List.of(mock(Cleaner.class));
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");

        when(availabilityStrategy.getAvailableCleaners(cleanerList, timeSlotDto)).thenReturn(cleanerList);

        List<Cleaner> result = checkAvailabilityHelper.getAvailableCleaners(cleanerList, timeSlotDto);

        assertNotNull(result);
        assertEquals(cleanerList, result);
        verify(availabilityStrategy, times(1)).getAvailableCleaners(cleanerList, timeSlotDto);
    }

    @Test
    void testIsCleanersAvailable() {
        List<CleanerDto> cleanerDtoList = List.of(mock(CleanerDto.class));
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");

        when(availabilityStrategy.isCleanersAvailable(cleanerDtoList, timeSlotDto)).thenReturn(true);

        boolean result = checkAvailabilityHelper.isCleanersAvailable(cleanerDtoList, timeSlotDto);

        assertTrue(result);
        verify(availabilityStrategy, times(1)).isCleanersAvailable(cleanerDtoList, timeSlotDto);
    }

    @Test
    void testGetAvailableHoursOfCleaner_NoBookings() {
        Cleaner cleaner = mock(Cleaner.class);
        String date = "2023-10-02";
        Timestamp bookingDate = DateTimeUtil.stringDateToTimestamp(date);
        Timestamp nextBookingDate = DateTimeUtil.addOneDayCalendar(date);

        when(bookingRepository.findAllByCleanersAndStartDateBetween(Set.of(cleaner), bookingDate, nextBookingDate))
                .thenReturn(new ArrayList<>());

        List<TimeSlotDto> result = checkAvailabilityHelper.getAvailableHoursOfCleaner(cleaner, date);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(String.format("%02d:00:00", BusinessConstants.SERVICE_START_HOUR), result.get(0).getStartTime());
        assertEquals(String.format("%02d:00:00", BusinessConstants.SERVICE_END_HOUR), result.get(0).getEndTime());
        verify(bookingRepository, times(1)).findAllByCleanersAndStartDateBetween(Set.of(cleaner), bookingDate, nextBookingDate);
    }

    @Test
    void testGetAvailableHoursOfCleaner_WithBookings() {
        Cleaner cleaner = mock(Cleaner.class);
        String date = "2023-10-02";
        Timestamp bookingDate = DateTimeUtil.stringDateToTimestamp(date);
        Timestamp nextBookingDate = DateTimeUtil.addOneDayCalendar(date);

        Booking booking = mock(Booking.class);
        when(booking.getStartDate()).thenReturn(Timestamp.valueOf("2023-10-02 10:00:00"));
        when(booking.getEndDate()).thenReturn(Timestamp.valueOf("2023-10-02 12:00:00"));

        when(bookingRepository.findAllByCleanersAndStartDateBetween(Set.of(cleaner), bookingDate, nextBookingDate))
                .thenReturn(List.of());

        List<TimeSlotDto> result = checkAvailabilityHelper.getAvailableHoursOfCleaner(cleaner, date);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(bookingRepository, times(1)).findAllByCleanersAndStartDateBetween(Set.of(cleaner), bookingDate, nextBookingDate);
    }
}