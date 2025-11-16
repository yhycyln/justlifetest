package com.example.justlifetest.api.controller;

import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.service.interfaces.BookingQueryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BookingApiControllerFunctionalTest {

    private final MockMvc mockMvc;

    @Mock
    private BookingQueryService bookingQueryService;

    @Autowired
    BookingApiControllerFunctionalTest(BookingApiController bookingApiController) {
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookingApiController).build();
    }

    @Test
    void testGetHello() throws Exception {
        mockMvc.perform(post("/api/booking/hello")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, JustLife World!"));
    }

    @Test
    void testGetCleanerAvailabilityWithDate() throws Exception {
        AvailabilityDto mockAvailability = new AvailabilityDto();
        when(bookingQueryService.getCleanerAvailabilityWithDate("2023-10-01"))
                .thenReturn(mockAvailability);

        mockMvc.perform(get("/api/booking/availabilityWithDate")
                        .param("date", "2023-10-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBookings() throws Exception {
        BookingDto mockBooking = new BookingDto();
        when(bookingQueryService.getBookings(List.of("1", "2", "3")))
                .thenReturn(Collections.singletonList(mockBooking));

        mockMvc.perform(get("/api/booking/getBookings")
                        .param("bookingIdList", "1", "2", "3")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}