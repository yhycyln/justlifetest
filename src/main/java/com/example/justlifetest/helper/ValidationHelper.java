package com.example.justlifetest.helper;

import com.example.justlifetest.constants.BusinessConstants;
import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.factory.ObjectFactory;
import com.example.justlifetest.util.DateTimeUtil;
import com.example.justlifetest.util.StringUtil;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ValidationHelper {

    /**
     * Validate booking request date
     * @param date booking date string in format "yyyy-MM-dd"
     * @throws IllegalArgumentException if date is null or empty or booking date is friday
     */
    public void validateRequest(String date) {
        if (StringUtil.isNullOrEmpty(date)) {
            throw new IllegalArgumentException("Date is required.");
        }

        // check date is not friday
        if (DateTimeUtil.isFriday(date)) {
            throw new IllegalArgumentException("Booking date is outside of service days");
        }
    }

    /**
     * Validate cleaner count and time slot
     * @param cleanerCount number of cleaners
     * @param timeSlotDto time slot dto
     * @throws IllegalArgumentException if cleaner count is invalid or time slot is invalid
     */
    public void validateCleanerCountAndTimeSlot(int cleanerCount, TimeSlotDto timeSlotDto) {
        if (cleanerCount < BusinessConstants.MIN_CLEANER_COUNT
                || cleanerCount > BusinessConstants.MAX_CLEANER_COUNT) {
            throw new IllegalArgumentException("Invalid cleaner count");
        }
        Timestamp startTime = DateTimeUtil.stringToTimestamp(timeSlotDto.getDate().concat(" ").concat(timeSlotDto.getStartTime()).concat(BusinessConstants.DEFAULT_SECONDS_FOR_DATE));
        if (DateTimeUtil.getCurrentTimestamp().isAfter(startTime.toLocalDateTime())) {
            throw new IllegalArgumentException("Booking time is in the past");
        }

        checkServiceHours(timeSlotDto);
    }

    /**
     * Check if the time slot is within service hours and not on a friday
     * @param timeSlotDto time slot dto
     * @throws IllegalArgumentException if date is friday or time slot is outside of service hours
     */
    public void checkServiceHours(TimeSlotDto timeSlotDto) {
        // check date is not friday
        if (DateTimeUtil.isFriday(timeSlotDto.getDate())) {
            throw new IllegalArgumentException("Booking date is outside of service days");
        }

        // check service hours is valid
        int startHour = Integer.parseInt(timeSlotDto.getStartTime().substring(0, 2));
        int endHour = Integer.parseInt(timeSlotDto.getEndTime().substring(0, 2));
        if (startHour < BusinessConstants.SERVICE_START_HOUR
                || endHour > BusinessConstants.SERVICE_END_HOUR
                || !BusinessConstants.SERVICE_HOUR_LIST.contains(endHour - startHour)) {
            throw new IllegalArgumentException("Booking time is outside of service hours");
        }
    }

    /**
     * Validate request with time slot
     * @param date - date string in format "yyyy-MM-dd"
     * @param startTime - time string in format "HH:mm"
     * @param endTime - time string in format "HH:mm"
     * @throws IllegalArgumentException if date is null or empty or booking date is friday or time slot is outside of service hours
     */
    public void validateRequestWithTimeSlot(String date, String startTime, String endTime) {
        validateRequest(date);
        checkServiceHours(
                ObjectFactory.createTimeSlotDto(date, startTime, endTime)
        );
    }
}
