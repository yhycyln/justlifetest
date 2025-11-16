package com.example.justlifetest.helper.availability;

import com.example.justlifetest.constants.BusinessConstants;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.model.Booking;
import com.example.justlifetest.model.Cleaner;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import com.example.justlifetest.util.DateTimeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DefaultAvailabilityStrategy implements AvailabilityStrategy {

    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;

    public DefaultAvailabilityStrategy(BookingRepository bookingRepository,
                                       CleanerRepository cleanerRepository) {
        this.bookingRepository = bookingRepository;
        this.cleanerRepository = cleanerRepository;
    }

    /**
     * Get available cleaners for the new booking timeslot
     * @param cleanerList list of cleaners to check
     * @param newBookingTimeslot new booking timeslot
     * @return list of available cleaners
     */
    @Override
    public List<Cleaner> getAvailableCleaners(List<Cleaner> cleanerList, TimeSlotDto newBookingTimeslot) {
        List<Cleaner> availableCleaners = new ArrayList<>();
        for (Cleaner cleaner : cleanerList) {
            if (isCleanerAvailable(cleaner.getId(), newBookingTimeslot)) {
                availableCleaners.add(cleaner);
            }
        }
        return availableCleaners;
    }

    /**
     * Check if all cleaners are available for the new booking timeslot
     * @param cleanerList list of cleaners to check
     * @param newBookingTimeslot new booking timeslot
     * @return true if all cleaners are available, false otherwise
     */
    @Override
    public boolean isCleanersAvailable(List<CleanerDto> cleanerList, TimeSlotDto newBookingTimeslot) {
        for (CleanerDto cleanerDto : cleanerList) {
            if (!isCleanerAvailable(cleanerDto.getId(), newBookingTimeslot)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a cleaner is available for the new booking timeslot
     * @param cleanerId cleaner id
     * @param newBookingTimeslot new booking timeslot
     * @return true if cleaner is available, false otherwise
     */
    private boolean isCleanerAvailable(long cleanerId, TimeSlotDto newBookingTimeslot) {
        // checking cleaner availability
        return !hasConflictingBooking(cleanerId, newBookingTimeslot);
    }

    /**
     * Check if a cleaner has conflicting bookings for the new booking timeslot
     * @param cleanerId cleaner id
     * @param newBookingTimeslot new booking timeslot
     * @return true if cleaner has conflicting bookings, false otherwise
     */
    private boolean hasConflictingBooking(long cleanerId, TimeSlotDto newBookingTimeslot) {
        Cleaner cleaner = cleanerRepository.findById(cleanerId)
                .orElseThrow(() -> new IllegalArgumentException("Cleaner not found with id: " + cleanerId));
        Set<Booking> bookingList = bookingRepository.findAllByCleaners(List.of(cleaner));

        for (Booking booking : bookingList) {
            // check if booking conflicts with dateTime
            long startTimeInMilliSeconds = booking.getStartDate().getTime();
            long endTimeInMilliSeconds = booking.getEndDate().getTime();

            // New booking times to timestamp
            Timestamp newStartTime = DateTimeUtil.stringToTimestamp(
                    newBookingTimeslot.getDate().concat(" ").concat(newBookingTimeslot.getStartTime()));
            Timestamp newEndTime = DateTimeUtil.stringToTimestamp(
                    newBookingTimeslot.getDate().concat(" ").concat(newBookingTimeslot.getEndTime()));

            long newStartTimeInMilliSeconds = newStartTime.getTime();
            long newEndTimeInMilliSeconds = newEndTime.getTime();

            // check timestamps for conflict and break time
            if ((newStartTimeInMilliSeconds > startTimeInMilliSeconds && newStartTimeInMilliSeconds < endTimeInMilliSeconds)
                    || (newEndTimeInMilliSeconds > startTimeInMilliSeconds && newEndTimeInMilliSeconds < endTimeInMilliSeconds)
                    || (endTimeInMilliSeconds + BusinessConstants.BREAK_TIME_IN_MILLISECONDS <= newEndTimeInMilliSeconds)
            ) {
                return true;
            }
        }
        return false;
    }
}
