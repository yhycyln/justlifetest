package com.example.justlifetest.service;

import com.example.justlifetest.dto.*;
import com.example.justlifetest.helper.CheckAvailabilityHelper;
import com.example.justlifetest.helper.ModelToDtoAdapter;
import com.example.justlifetest.helper.ValidationHelper;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import com.example.justlifetest.repository.VehicleRepository;
import com.example.justlifetest.service.impls.BookingQueryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class BookingQueryServiceImplUnitTest {

    private BookingQueryServiceImpl bookingQueryService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CleanerRepository cleanerRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private CheckAvailabilityHelper checkAvailabilityHelper;

    @Mock
    private ValidationHelper validationHelper;
    @Mock
    private ModelToDtoAdapter modelToDtoAdapter;

    @BeforeEach
    void setUp() {
        bookingQueryService = new BookingQueryServiceImpl(
                bookingRepository,
                cleanerRepository,
                vehicleRepository,
                checkAvailabilityHelper,
                validationHelper,
                modelToDtoAdapter);
    }

    @Test
    void testGetCleanerAvailabilityWithDate_Success() {
        String date = "2023-10-02";

        Cleaner cleaner = new Cleaner();
        cleaner.setId(1L);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        cleaner.setVehicle(vehicle);

        when(cleanerRepository.findAllCleaner()).thenReturn(List.of(cleaner));
        when(checkAvailabilityHelper.getAvailableHoursOfCleaner(cleaner, date)).thenReturn(List.of(new TimeSlotDto(date, "09:00", "11:00")));

        AvailabilityDto result = bookingQueryService.getCleanerAvailabilityWithDate(date);

        assertNotNull(result);
        assertEquals(1, result.getAvailableVehicleDtoList().size());
        verify(validationHelper, times(1)).validateRequest(date);
        verify(cleanerRepository, times(1)).findAllCleaner();
    }

    @Test
    void testGetCleanerAvailabilityWithTimeSlot_Success() {
        String date = "2023-10-02";
        String startTime = "09:00";
        String endTime = "11:00";
        TimeSlotDto timeSlotDto = new TimeSlotDto(date, startTime, endTime);

        Cleaner cleaner = new Cleaner();
        cleaner.setId(1L);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        cleaner.setVehicle(vehicle);

        when(cleanerRepository.findAllCleaner()).thenReturn(List.of(cleaner));
        when(checkAvailabilityHelper.getAvailableCleaners(List.of(cleaner), timeSlotDto)).thenReturn(List.of(cleaner));

        AvailabilityDto result = bookingQueryService.getCleanerAvailabilityWithTimeSlot(date, startTime, endTime);

        assertNotNull(result);
        assertEquals(1, result.getAvailableVehicleDtoList().size());
        verify(validationHelper, times(1)).validateRequestWithTimeSlot(date, startTime, endTime);
        verify(cleanerRepository, times(1)).findAllCleaner();
    }

    @Test
    void testGetBookings_Success() {
        List<String> bookingIdList = List.of("1", "2");

        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.getBookingsByIdList(bookingIdList)).thenReturn(List.of(booking));
        when(modelToDtoAdapter.mapBookingToDto(booking)).thenReturn(new BookingDto());

        List<BookingDto> result = bookingQueryService.getBookings(bookingIdList);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).getBookingsByIdList(bookingIdList);
    }
}