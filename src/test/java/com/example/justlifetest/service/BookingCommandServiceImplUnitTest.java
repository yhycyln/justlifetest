package com.example.justlifetest.service;

import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.helper.CheckAvailabilityHelper;
import com.example.justlifetest.helper.ModelToDtoAdapter;
import com.example.justlifetest.helper.ValidationHelper;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import com.example.justlifetest.repository.VehicleRepository;
import com.example.justlifetest.service.impls.BookingCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class BookingCommandServiceImplUnitTest {

    private BookingCommandServiceImpl bookingCommandService;

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
        MockitoAnnotations.openMocks(this);
        bookingCommandService = new BookingCommandServiceImpl(
                bookingRepository,
                cleanerRepository,
                vehicleRepository,
                checkAvailabilityHelper,
                validationHelper,
                modelToDtoAdapter);
    }

    @Test
    void testCreateBooking_Success() {
        BookingRequestDto requestDto = new BookingRequestDto();
        requestDto.setCleanerCount(1);
        requestDto.setVehicleId(0L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");
        requestDto.setTimeSlotDto(timeSlotDto);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        Cleaner cleaner = new Cleaner();
        List<Cleaner> cleanerList = List.of(cleaner);

        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        when(cleanerRepository.findAllByVehicleId(vehicle.getId())).thenReturn(cleanerList);
        when(checkAvailabilityHelper.getAvailableCleaners(cleanerList, timeSlotDto)).thenReturn(cleanerList);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingDto result = bookingCommandService.createBooking(requestDto);

        assertNotNull(result);
        verify(validationHelper, times(1)).validateCleanerCountAndTimeSlot(requestDto.getCleanerCount(), timeSlotDto);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testCreateBooking_NoVehicleAvailable() {
        BookingRequestDto requestDto = new BookingRequestDto();
        requestDto.setCleanerCount(1);
        requestDto.setVehicleId(0L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");
        requestDto.setTimeSlotDto(timeSlotDto);

        when(vehicleRepository.findAll()).thenReturn(List.of());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookingCommandService.createBooking(requestDto));

        assertEquals("No vehicle available for the requested number of cleaners", exception.getMessage());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    void testUpdateBooking_Success() {
        BookingUpdateRequestDto requestDto = new BookingUpdateRequestDto();
        requestDto.setBookingId(1L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");
        requestDto.setTimeSlotDto(timeSlotDto);
        CleanerDto cleanerDto = new CleanerDto();
        cleanerDto.setId(1L);
        requestDto.setCleanerList(List.of(cleanerDto));

        Cleaner cleaner = new Cleaner();
        when(checkAvailabilityHelper.isCleanersAvailable(requestDto.getCleanerList(), timeSlotDto)).thenReturn(true);
        when(cleanerRepository.findAllById(anyList())).thenReturn(List.of(cleaner));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BookingDto result = bookingCommandService.updateBooking(requestDto);

        assertNotNull(result);
        verify(validationHelper, times(1)).validateCleanerCountAndTimeSlot(requestDto.getCleanerList().size(), timeSlotDto);
        verify(bookingRepository, times(1)).deleteById(requestDto.getBookingId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testUpdateBooking_CleanersNotAvailable() {
        BookingUpdateRequestDto requestDto = new BookingUpdateRequestDto();
        requestDto.setBookingId(1L);
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");
        requestDto.setTimeSlotDto(timeSlotDto);
        CleanerDto cleanerDto = new CleanerDto();
        cleanerDto.setId(1L);
        requestDto.setCleanerList(List.of(cleanerDto));

        when(checkAvailabilityHelper.isCleanersAvailable(requestDto.getCleanerList(), timeSlotDto)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookingCommandService.updateBooking(requestDto));

        assertEquals("One or more cleaners are not available for the requested time slot", exception.getMessage());
        verify(checkAvailabilityHelper, times(1)).isCleanersAvailable(requestDto.getCleanerList(), timeSlotDto);
    }
}