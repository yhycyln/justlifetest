package com.example.justlifetest.service;

import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import com.example.justlifetest.repository.VehicleRepository;
import com.example.justlifetest.service.impls.BookingCommandServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Import(BookingCommandServiceImpl.class)
class BookingCommandServiceImplFunctionalTest {

    @Autowired
    private BookingCommandServiceImpl bookingCommandService;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CleanerRepository cleanerRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @BeforeEach
    void setUp() {
        // Initialize test data
        Cleaner cleaner1 = new Cleaner();
        cleaner1.setName("John");
        cleaner1.setSurname("Doe");

        Cleaner cleaner2 = new Cleaner();
        cleaner2.setName("Jane");
        cleaner2.setSurname("Smith");

        Vehicle vehicle = new Vehicle();
        vehicle.setName("Vehicle 1");

        cleaner1.setVehicle(vehicle);
        cleaner2.setVehicle(vehicle);

        vehicleRepository.save(vehicle);
        cleanerRepository.saveAll(List.of(cleaner1, cleaner2));
    }

    @Test
    void testCreateBooking_Success() {
        BookingRequestDto requestDto = new BookingRequestDto();
        requestDto.setCleanerCount(2);
        requestDto.setVehicleId(0L);
        requestDto.setTimeSlotDto(new TimeSlotDto("2023-10-02", "09:00", "11:00"));

        BookingDto result = bookingCommandService.createBooking(requestDto);

        assertNotNull(result);
        assertEquals(2, result.getCleanerDtoList().size());
        assertEquals("2023-10-02", result.getTimeSlotDto().getDate());
        assertEquals("09:00", result.getTimeSlotDto().getStartTime());
        assertEquals("11:00", result.getTimeSlotDto().getEndTime());

        List<Booking> bookings = bookingRepository.findAll();
        assertEquals(1, bookings.size());
    }

    @Test
    void testCreateBooking_NoVehicleAvailable() {
        BookingRequestDto requestDto = new BookingRequestDto();
        requestDto.setCleanerCount(3); // More cleaners than available
        requestDto.setVehicleId(0L);
        requestDto.setTimeSlotDto(new TimeSlotDto("2023-10-02", "09:00", "11:00"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookingCommandService.createBooking(requestDto));

        assertEquals("No vehicle available for the requested number of cleaners", exception.getMessage());
    }

    @Test
    void testUpdateBooking_Success() {
        // Create an initial booking
        BookingRequestDto createRequest = new BookingRequestDto();
        createRequest.setCleanerCount(1);
        createRequest.setVehicleId(0L);
        createRequest.setTimeSlotDto(new TimeSlotDto("2023-10-02", "09:00", "11:00"));
        BookingDto createdBooking = bookingCommandService.createBooking(createRequest);

        // Update the booking
        BookingUpdateRequestDto updateRequest = new BookingUpdateRequestDto();
        updateRequest.setBookingId(bookingRepository.findAll().get(0).getId());
        updateRequest.setTimeSlotDto(new TimeSlotDto("2023-10-02", "12:00", "14:00"));
        updateRequest.setCleanerList(createdBooking.getCleanerDtoList());

        BookingDto updatedBooking = bookingCommandService.updateBooking(updateRequest);

        assertNotNull(updatedBooking);
        assertEquals("2023-10-02", updatedBooking.getTimeSlotDto().getDate());
        assertEquals("12:00", updatedBooking.getTimeSlotDto().getStartTime());
        assertEquals("14:00", updatedBooking.getTimeSlotDto().getEndTime());
    }

    @Test
    void testUpdateBooking_CleanersNotAvailable() {
        // Create an initial booking
        BookingRequestDto createRequest = new BookingRequestDto();
        createRequest.setCleanerCount(1);
        createRequest.setVehicleId(0L);
        createRequest.setTimeSlotDto(new TimeSlotDto("2023-10-02", "09:00", "11:00"));
        bookingCommandService.createBooking(createRequest);

        // Attempt to update the booking with unavailable cleaners
        BookingUpdateRequestDto updateRequest = new BookingUpdateRequestDto();
        updateRequest.setBookingId(bookingRepository.findAll().get(0).getId());
        updateRequest.setTimeSlotDto(new TimeSlotDto("2023-10-02", "12:00", "14:00"));
        updateRequest.setCleanerList(List.of()); // No cleaners provided

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                bookingCommandService.updateBooking(updateRequest));

        assertEquals("One or more cleaners are not available for the requested time slot", exception.getMessage());
    }
}