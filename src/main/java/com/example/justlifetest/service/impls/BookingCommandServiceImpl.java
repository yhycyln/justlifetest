package com.example.justlifetest.service.impls;

import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.factory.ObjectFactory;
import com.example.justlifetest.helper.CheckAvailabilityHelper;
import com.example.justlifetest.helper.ValidationHelper;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import com.example.justlifetest.repository.VehicleRepository;
import com.example.justlifetest.service.interfaces.BookingCommandService;
import com.example.justlifetest.util.MappingHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class BookingCommandServiceImpl implements BookingCommandService {

    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;
    private final VehicleRepository vehicleRepository;
    private final CheckAvailabilityHelper checkAvailabilityHelper;
    private final ValidationHelper validationHelper;

    public BookingCommandServiceImpl(BookingRepository bookingRepository,
                                     CleanerRepository cleanerRepository,
                                     VehicleRepository vehicleRepository,
                                     CheckAvailabilityHelper checkAvailabilityHelper,
                                     ValidationHelper validationHelper) {
        this.bookingRepository = bookingRepository;
        this.cleanerRepository = cleanerRepository;
        this.vehicleRepository = vehicleRepository;
        this.checkAvailabilityHelper = checkAvailabilityHelper;
        this.validationHelper = validationHelper;
    }

    @Override
    public BookingDto createBooking(BookingRequestDto requestDto) {
        TimeSlotDto timeSlotDto = requestDto.getTimeSlotDto();
        validationHelper.validateCleanerCountAndTimeSlot(requestDto.getCleanerCount(), timeSlotDto);

        // get available vehicle for the requested number of cleaners
        // consider no vehicle id is provided in the request
        if (requestDto.getVehicleId() != 0L) {
            List<Vehicle> vehicleList = vehicleRepository.findAll();
            for (Vehicle vehicle : vehicleList) {
                List<Cleaner> cleanerList = cleanerRepository.findAllByVehicleId(vehicle.getId());
                List<Cleaner> availableCleaners = checkAvailabilityHelper.getAvailableCleaners(cleanerList, timeSlotDto);
                if (availableCleaners.size() >= requestDto.getCleanerCount()) {
                    requestDto.setVehicleId(vehicle.getId()); // set the vehicle id in the request dto, vehicle selected for service
                    break;
                }
            }
        }
        if (requestDto.getVehicleId() == 0L) { // if still no vehicle id is set, means no vehicle available
            throw new IllegalArgumentException("No vehicle available for the requested number of cleaners");
        }
        List<Cleaner> cleanerList = cleanerRepository.findAllByVehicleId(requestDto.getVehicleId());
        List<Cleaner> availableCleaners = checkAvailabilityHelper.getAvailableCleaners(cleanerList, timeSlotDto);

        if (availableCleaners.size() < requestDto.getCleanerCount()) {
            throw new IllegalArgumentException("Requested number of cleaners not available");
        }

        Booking booking = ObjectFactory.createBooking(
                timeSlotDto,
                availableCleaners.subList(0, requestDto.getCleanerCount()));
        bookingRepository.save(booking);

        return MappingHelper.map(booking, BookingDto.class);
    }

    public BookingDto updateBooking(BookingUpdateRequestDto requestDto) {
        TimeSlotDto timeSlotDto = requestDto.getTimeSlotDto();
        validationHelper.validateCleanerCountAndTimeSlot(requestDto.getCleanerList().size(), timeSlotDto);

        bookingRepository.deleteById(requestDto.getBookingId());
        // cleaner <-> booking mapping will be removed due to cascade delete

        if (!checkAvailabilityHelper.isCleanersAvailable(requestDto.getCleanerList(), timeSlotDto)) {
            throw new IllegalArgumentException("One or more cleaners are not available for the requested time slot");
        }

        Booking newBooking = ObjectFactory.createBooking(
                timeSlotDto,
                cleanerRepository.findAllById(requestDto.getCleanerList().stream()
                        .map(CleanerDto::getId).toList())
        );
        bookingRepository.save(newBooking);

        return MappingHelper.map(newBooking, BookingDto.class);
    }

}
