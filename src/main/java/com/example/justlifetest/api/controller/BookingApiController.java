package com.example.justlifetest.api.controller;

import com.example.justlifetest.api.constants.ApiEndpoints;
import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.api.response.ResponseOfGet;
import com.example.justlifetest.api.response.ResponseOfGetList;
import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.service.interfaces.BookingCommandService;
import com.example.justlifetest.service.interfaces.BookingQueryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ApiEndpoints.BOOKING_API_URL, produces = ApiEndpoints.RESPONSE_CONTENT_TYPE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BookingApiController {
    private final BookingCommandService bookingCommandService;
    private final BookingQueryService bookingQueryService;

    public BookingApiController(BookingCommandService bookingCommandService,
                                BookingQueryService bookingQueryService) {
        this.bookingCommandService = bookingCommandService;
        this.bookingQueryService = bookingQueryService;
    }

    @PostMapping(value = "/hello", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "getHello api", tags = "Returns hello", notes = "Returns hello")
    public String getHello() {
        return "Hello, JustLife World!";
    }

    @PostMapping(value = "/createBooking", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create Booking API", tags = "Returns created booking info", notes = "Returns created booking info")
    public BookingDto createBooking(@RequestBody BookingRequestDto requestDto) {
        return bookingCommandService.createBooking(requestDto);
    }

    @PostMapping(value = "/updateBooking", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Create Booking API", tags = "Returns created booking info", notes = "Returns created booking info")
    public BookingDto updateBooking(@RequestBody BookingUpdateRequestDto requestDto) {
        return bookingCommandService.updateBooking(requestDto);
    }

    @GetMapping(value = "/availabilityWithDate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get Cleaner Availability API", tags = "Returns availability of cleaners", notes = "Returns availability of cleaners")
    public ResponseOfGet<AvailabilityDto> getCleanerAvailabilityWithDate(@RequestParam String date) {
        return new ResponseOfGet<>(bookingQueryService.getCleanerAvailabilityWithDate(date));
    }

    @GetMapping(value = "/availabilityWithTimeSlot", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get Cleaner Availability API", tags = "Returns availability of cleaners", notes = "Returns availability of cleaners")
    public ResponseOfGet<AvailabilityDto> getCleanerAvailabilityWithTimeSlot(@RequestParam String date,
                                                                @RequestParam String startTime,
                                                                @RequestParam String endTime) {
        return new ResponseOfGet<>(bookingQueryService.getCleanerAvailabilityWithTimeSlot(date, startTime, endTime));
    }

    @GetMapping(value = "/getBookings", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get Booking List API", tags = "Returns booking list", notes = "Returns booking list")
    public ResponseOfGetList<BookingDto> getBookings(@RequestParam List<String> bookingIdList) {
        return new ResponseOfGetList<>(bookingQueryService.getBookings(bookingIdList));
    }

}
