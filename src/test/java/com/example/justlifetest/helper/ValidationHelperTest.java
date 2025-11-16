package com.example.justlifetest.helper;

import com.example.justlifetest.dto.TimeSlotDto;
import com.example.justlifetest.util.DateTimeUtil;
import com.example.justlifetest.util.StringUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

class ValidationHelperTest {

    private ValidationHelper validationHelper;

    @BeforeEach
    void setUp() {
        validationHelper = new ValidationHelper();
    }

    @Test
    void testValidateRequest_ValidDate() {
        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-02")).thenReturn(false);

            assertDoesNotThrow(() -> validationHelper.validateRequest("2023-10-02"));
        }
    }

    @Test
    void testValidateRequest_NullOrEmptyDate() {
        try (MockedStatic<StringUtil> mockedStringUtil = mockStatic(StringUtil.class)) {
            mockedStringUtil.when(() -> StringUtil.isNullOrEmpty(null)).thenReturn(true);
            mockedStringUtil.when(() -> StringUtil.isNullOrEmpty("")).thenReturn(true);

            assertThrows(IllegalArgumentException.class, () -> validationHelper.validateRequest(null));
            assertThrows(IllegalArgumentException.class, () -> validationHelper.validateRequest(""));
        }
    }

    @Test
    void testValidateRequest_FridayDate() {
        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-06")).thenReturn(true);

            assertThrows(IllegalArgumentException.class, () -> validationHelper.validateRequest("2023-10-06"));
        }
    }

    @Test
    void testValidateCleanerCountAndTimeSlot_ValidInput() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");

        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-02")).thenReturn(false);

            assertDoesNotThrow(() -> validationHelper.validateCleanerCountAndTimeSlot(2, timeSlotDto));
        }
    }

    @Test
    void testValidateCleanerCountAndTimeSlot_InvalidCleanerCount() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");

        assertThrows(IllegalArgumentException.class, () -> validationHelper.validateCleanerCountAndTimeSlot(0, timeSlotDto));
        assertThrows(IllegalArgumentException.class, () -> validationHelper.validateCleanerCountAndTimeSlot(10, timeSlotDto));
    }

    @Test
    void testCheckServiceHours_ValidTimeSlot() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "09:00", "11:00");

        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-02")).thenReturn(false);

            assertDoesNotThrow(() -> validationHelper.checkServiceHours(timeSlotDto));
        }
    }

    @Test
    void testCheckServiceHours_InvalidTimeSlot() {
        TimeSlotDto timeSlotDto = new TimeSlotDto("2023-10-02", "06:00", "08:00");

        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-02")).thenReturn(false);

            assertThrows(IllegalArgumentException.class, () -> validationHelper.checkServiceHours(timeSlotDto));
        }
    }

    @Test
    void testValidateRequestWithTimeSlot_ValidInput() {
        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-02")).thenReturn(false);

            assertDoesNotThrow(() -> validationHelper.validateRequestWithTimeSlot("2023-10-02", "09:00", "11:00"));
        }
    }

    @Test
    void testValidateRequestWithTimeSlot_InvalidInput() {
        try (MockedStatic<DateTimeUtil> mockedDateTimeUtil = mockStatic(DateTimeUtil.class)) {
            mockedDateTimeUtil.when(() -> DateTimeUtil.isFriday("2023-10-06")).thenReturn(true);

            assertThrows(IllegalArgumentException.class, () -> validationHelper.validateRequestWithTimeSlot("2023-10-06", "09:00", "11:00"));
        }
    }
}