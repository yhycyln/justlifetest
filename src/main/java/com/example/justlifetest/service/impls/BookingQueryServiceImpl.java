package com.example.justlifetest.service.impls;

import com.example.justlifetest.dto.*;
import com.example.justlifetest.factory.ObjectFactory;
import com.example.justlifetest.helper.CheckAvailabilityHelper;
import com.example.justlifetest.helper.ModelToDtoAdapter;
import com.example.justlifetest.helper.ValidationHelper;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.model.Vehicle;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import com.example.justlifetest.repository.VehicleRepository;
import com.example.justlifetest.service.interfaces.BookingQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookingQueryServiceImpl implements BookingQueryService {

    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;
    private final VehicleRepository vehicleRepository;
    private final CheckAvailabilityHelper checkAvailabilityHelper;
    private final ValidationHelper validationHelper;
    private final ModelToDtoAdapter modelToDtoAdapter;

    public BookingQueryServiceImpl(BookingRepository bookingRepository,
                                   CleanerRepository cleanerRepository,
                                   VehicleRepository vehicleRepository,
                                   CheckAvailabilityHelper checkAvailabilityHelper,
                                   ValidationHelper validationHelper,
                                   ModelToDtoAdapter modelToDtoAdapter) {
        this.bookingRepository = bookingRepository;
        this.cleanerRepository = cleanerRepository;
        this.vehicleRepository = vehicleRepository;
        this.checkAvailabilityHelper = checkAvailabilityHelper;
        this.validationHelper = validationHelper;
        this.modelToDtoAdapter = modelToDtoAdapter;
    }

    /**
     * Get cleaner availability with date
     * @param date - date string in format "yyyy-MM-dd"
     * @return AvailabilityDto
     */
    @Override
    public AvailabilityDto getCleanerAvailabilityWithDate(String date) {
        validationHelper.validateRequest(date);

        List<Cleaner> cleanerList = cleanerRepository.findAllCleaner();

        Map<Vehicle, List<Cleaner>> vehicleListMap = cleanerList.stream()
                .collect(Collectors.groupingBy(Cleaner::getVehicle));// Group by the Vehicle object

        List<AvailableVehicleDto> availableVehicleDtoList = new ArrayList<>();
        // return cleaner list with all time slots as available
        for (Map.Entry<Vehicle, List<Cleaner>> entry : vehicleListMap.entrySet()) {
            availableVehicleDtoList.add(
                    ObjectFactory.createAvailableVehicleDto(
                            entry.getKey().getId(),
                            entry.getValue().stream().collect(
                                    Collectors.toMap(
                                            modelToDtoAdapter::mapCleanerToDto,
                                            cleaner -> checkAvailabilityHelper.getAvailableHoursOfCleaner(cleaner, date)
                                    )
                            )
                    )
            );
        }

        return ObjectFactory.createAvailabilityDto(availableVehicleDtoList);
    }

    /**
     * Get cleaner availability with date and time slot
     * @param date - date string in format "yyyy-MM-dd"
     * @param startTime - time string in format "HH:mm"
     * @param endTime - time string in format "HH:mm"
     * @return AvailabilityDto
     */
    @Override
    public AvailabilityDto getCleanerAvailabilityWithTimeSlot(String date, String startTime, String endTime) {
        validationHelper.validateRequestWithTimeSlot(date, startTime, endTime);
        TimeSlotDto timeSlotDto = ObjectFactory.createTimeSlotDto(date, startTime, endTime);

        List<Cleaner> cleanerList = cleanerRepository.findAllCleaner();
        // return cleaner list with the selected time slots based on availability
        List<Cleaner> availableCleaners = checkAvailabilityHelper.getAvailableCleaners(cleanerList, timeSlotDto);

        Map<Vehicle, List<Cleaner>> vehicleListMap = availableCleaners.stream()
                .collect(Collectors.groupingBy(Cleaner::getVehicle));// Group by the Vehicle object

        List<AvailableVehicleDto> availableVehicleDtoList = new ArrayList<>();
        List<TimeSlotDto> availableTimeSlots = List.of(timeSlotDto);

        for (Map.Entry<Vehicle, List<Cleaner>> entry : vehicleListMap.entrySet()) {
            availableVehicleDtoList.add(
                    ObjectFactory.createAvailableVehicleDto(
                            entry.getKey().getId(),
                            entry.getValue().stream().collect(
                                    Collectors.toMap(
                                            modelToDtoAdapter::mapCleanerToDto,
                                            cleaner -> availableTimeSlots
                                    )
                            )
                    )
            );
        }
        return ObjectFactory.createAvailabilityDto(availableVehicleDtoList);
    }

    /**
     * Get bookings by booking id list
     * @param bookingIdList - list of booking ids
     * @return list of BookingDto
     */
    @Override
    public List<BookingDto> getBookings(List<String> bookingIdList) {
        List<Booking> bookingList = bookingRepository.getBookingsByIdList(bookingIdList);
        return bookingList.stream()
                .map(modelToDtoAdapter::mapBookingToDto)
                .toList();
    }

    /**
     * Get all bookings
     * @return list of BookingDto
     */
    @Override
    public List<BookingDto> getAllBookings() {
        List<Booking> bookingList = bookingRepository.findAllBookings();
        return bookingList.stream()
                .map(modelToDtoAdapter::mapBookingToDto)
                .toList();
    }

    /**
     * Get all vehicles
     * @return list of VehicleDto
     */
    @Override
    public List<VehicleDto> getAllVehicles() {
        List<Vehicle> vehicleList = vehicleRepository.findAllVehicles();
        return vehicleList.stream()
                .map(modelToDtoAdapter::mapVehicleToDto)
                .toList();
    }

    /**
     * Get all cleaners
     * @return list of CleanerDto
     */
    @Override
    public List<CleanerDto> getAllCleaners() {
        List<Cleaner> cleanerList = cleanerRepository.findAllCleaner();
        return cleanerList.stream()
                .map(modelToDtoAdapter::mapCleanerToDto)
                .toList();
    }

}
