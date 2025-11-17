package com.example.justlifetest.api.controller;

import com.example.justlifetest.api.request.BookingRequestDto;
import com.example.justlifetest.api.request.BookingUpdateRequestDto;
import com.example.justlifetest.api.response.ResponseOfGet;
import com.example.justlifetest.api.response.ResponseOfGetList;
import com.example.justlifetest.dto.AvailabilityDto;
import com.example.justlifetest.dto.BookingDto;
import com.example.justlifetest.dto.CleanerDto;
import com.example.justlifetest.dto.VehicleDto;
import com.example.justlifetest.service.interfaces.BookingCommandService;
import com.example.justlifetest.service.interfaces.BookingQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
class BookingApiControllerFunctionalTest {

    private MockMvc mockMvc;

    @Mock
    private BookingCommandService bookingCommandService;

    @Mock
    private BookingQueryService bookingQueryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingApiController bookingApiController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingApiController).build();
    }

    @Test
    void testGetHello() throws Exception {
        mockMvc.perform(post("/api/booking/hello")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, JustLife World!"));
    }

    @Test
    void testGenerateVehiclesAndCleaners() throws Exception {
        mockMvc.perform(post("/api/booking/generateVehiclesAndCleaners")
                        .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateBooking() throws Exception {
        BookingRequestDto requestDto = new BookingRequestDto();
        BookingDto responseDto = new BookingDto();
        when(bookingCommandService.createBooking(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/booking/createBooking")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void testUpdateBooking() throws Exception {
        BookingUpdateRequestDto requestDto = new BookingUpdateRequestDto();
        BookingDto responseDto = new BookingDto();
        when(bookingCommandService.updateBooking(requestDto)).thenReturn(responseDto);

        mockMvc.perform(post("/api/booking/updateBooking")
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    void testGetCleanerAvailabilityWithDate() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        when(bookingQueryService.getCleanerAvailabilityWithDate("2023-10-01")).thenReturn(availabilityDto);

        mockMvc.perform(get("/api/booking/availabilityWithDate")
                        .param("date", "2023-10-01")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseOfGet<>(availabilityDto))));
    }

    @Test
    void testGetCleanerAvailabilityWithTimeSlot() throws Exception {
        AvailabilityDto availabilityDto = new AvailabilityDto();
        when(bookingQueryService.getCleanerAvailabilityWithTimeSlot("2023-10-01", "09:00", "11:00")).thenReturn(availabilityDto);

        mockMvc.perform(get("/api/booking/availabilityWithTimeSlot")
                        .param("date", "2023-10-01")
                        .param("startTime", "09:00")
                        .param("endTime", "11:00")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseOfGet<>(availabilityDto))));
    }

    @Test
    void testGetBookings() throws Exception {
        List<BookingDto> bookingDtoList = List.of(new BookingDto());
        when(bookingQueryService.getBookings(List.of("1", "2", "3"))).thenReturn(bookingDtoList);

        mockMvc.perform(get("/api/booking/getBookings")
                        .param("bookingIdList", "1", "2", "3")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseOfGetList<>(bookingDtoList))));
    }

    @Test
    void testGetAllBookings() throws Exception {
        List<BookingDto> bookingDtoList = List.of(new BookingDto());
        when(bookingQueryService.getAllBookings()).thenReturn(bookingDtoList);

        mockMvc.perform(get("/api/booking/getAllBookings")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseOfGetList<>(bookingDtoList))));
    }

    @Test
    void testGetAllVehicles() throws Exception {
        List<VehicleDto> vehicleDtoList = List.of(new VehicleDto());
        when(bookingQueryService.getAllVehicles()).thenReturn(vehicleDtoList);

        mockMvc.perform(get("/api/booking/getAllVehicles")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseOfGetList<>(vehicleDtoList))));
    }

    @Test
    void testGetAllCleaners() throws Exception {
        List<CleanerDto> cleanerDtoList = List.of(new CleanerDto());
        when(bookingQueryService.getAllCleaners()).thenReturn(cleanerDtoList);

        mockMvc.perform(get("/api/booking/getAllCleaners")
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new ResponseOfGetList<>(cleanerDtoList))));
    }
}