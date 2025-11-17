package com.example.justlifetest.helper;

import com.example.justlifetest.constants.BusinessConstants;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.factory.ObjectFactory;
import com.example.justlifetest.helper.availability.AvailabilityStrategy;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.util.DateTimeUtil;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckAvailabilityHelper {

    private final BookingRepository bookingRepository;
    private final AvailabilityStrategy availabilityStrategy;

    public CheckAvailabilityHelper(BookingRepository bookingRepository,
                                   AvailabilityStrategy availabilityStrategy) {
        this.bookingRepository = bookingRepository;
        this.availabilityStrategy = availabilityStrategy;
    }

    /**
     * Get available cleaners for the new booking timeslot
     * @param cleanerList list of cleaners to check
     * @param newBookingTimeslot new booking timeslot
     * @return list of available cleaners
     */
    public List<Cleaner> getAvailableCleaners(List<Cleaner> cleanerList, TimeSlotDto newBookingTimeslot) {
        return availabilityStrategy.getAvailableCleaners(cleanerList, newBookingTimeslot);
    }

    /**
     * Check if all cleaners are available for the new booking timeslot
     * @param cleanerList list of cleaners to check
     * @param newBookingTimeslot new booking timeslot
     * @return true if all cleaners are available, false otherwise
     */
    public boolean isCleanersAvailable(List<CleanerDto> cleanerList, TimeSlotDto newBookingTimeslot) {
        return availabilityStrategy.isCleanersAvailable(cleanerList, newBookingTimeslot);
    }


    /**
     * Get available hours of a cleaner for a specific date
     * @param cleaner cleaner
     * @param date date in format "yyyy-MM-dd"
     * @return list of available time slots
     */
    public List<TimeSlotDto> getAvailableHoursOfCleaner(Cleaner cleaner, String date) {
        // check booking dates between date and start of next day
        Timestamp bookingDate = DateTimeUtil.stringDateToTimestamp(date);
        Timestamp nextBookingDate = DateTimeUtil.addOneDayCalendar(date);
        List<Booking> bookingList = bookingRepository.findAllByCleanersAndStartDateBetween(cleaner, bookingDate, nextBookingDate);

        List<TimeSlotDto> availableTimeSlots = new ArrayList<>();
        if (bookingList.isEmpty()) {
            // return all day
            TimeSlotDto timeSlotDto = ObjectFactory
                    .createTimeSlotDto(date,
                            String.format("%02d", BusinessConstants.SERVICE_START_HOUR).concat(BusinessConstants.DEFAULT_MINUTES_FOR_DATE_FORMAT),
                            String.valueOf(BusinessConstants.SERVICE_END_HOUR).concat(BusinessConstants.DEFAULT_MINUTES_FOR_DATE_FORMAT));
            availableTimeSlots.add(timeSlotDto);
        } else {
            // add time slots based on existing bookings plus 30 minutes and at least 2 hour long
            int serviceStartHour = BusinessConstants.SERVICE_START_HOUR;

            for (Booking booking : bookingList) {
                // Logic to find gaps between bookings and add to availableTimeSlots
                int bookingStartHour = booking.getStartDate().toLocalDateTime().getHour();
                if (bookingStartHour - serviceStartHour > 2) {
                    TimeSlotDto timeSlotDto = ObjectFactory
                            .createTimeSlotDto(date,
                                    String.format("%02d", serviceStartHour).concat(BusinessConstants.DEFAULT_MINUTES_FOR_DATE_FORMAT),
                                    String.format("%02d", bookingStartHour).concat(BusinessConstants.DEFAULT_MINUTES_FOR_DATE_FORMAT));
                    availableTimeSlots.add(timeSlotDto);
                }
                serviceStartHour = booking.getEndDate().toLocalDateTime().getHour() + 1; // adding break time
            }
        }
        return availableTimeSlots;

    }
}
