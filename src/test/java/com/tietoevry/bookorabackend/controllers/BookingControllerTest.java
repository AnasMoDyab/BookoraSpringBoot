package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.services.BookingService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("BookingController unit test")
@Tag("development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    BookingService bookingService;

    @InjectMocks
    BookingController bookingController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @AfterEach
    void tearDown() {
        reset(bookingService);
    }

    @Test
    void bookOneZoneOnOneDay() throws Exception {
        //given
        BookingIdDTO bookingIdDTO = new BookingIdDTO("test", 1L);
        given(bookingService.bookOneZoneOfOneDay(any())).willReturn(bookingIdDTO);

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"LocalDate\":\"2020-11-11\",\"employeeId\":\"1\",\"zoneId\":\"1\"}"))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("test")))
                .andExpect(jsonPath("$.bookingId", is(1)));
        then(bookingService).should(times(1)).bookOneZoneOfOneDay(any());
    }

/*    @Disabled
    @Test
    void getAllBookingOfEmployee() throws Exception {
        //given
        BookingListDTO list = new BookingListDTO(new ArrayList<>());
        LocalDate date = LocalDate.of(2020, 11, 11);
        BookingDTO bookingDTO = new BookingDTO(date, 1L, 1L);
        list.getBookingDTOList().add(bookingDTO);
        given(bookingService.getAllBookingOfEmployee(any())).willReturn(list);

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/getAllBookingOfEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingDTOList[0].date[0]", is(2020)))
                .andExpect(jsonPath("$.bookingDTOList[0].date[1]", is(11)))
                .andExpect(jsonPath("$.bookingDTOList[0].date[2]", is(11)))
                .andExpect(jsonPath("$.bookingDTOList[0].employeeId", is(1)))
                .andExpect(jsonPath("$.bookingDTOList[0].zoneId", is(1)));
        then(bookingService).should(times(1)).getAllBookingOfEmployee(any());
    }

    @Disabled
    @Test
    void getAllValidBookingOfEmployee() throws Exception {
        //given
        BookingListDTO list = new BookingListDTO(new ArrayList<>());
        LocalDate date = LocalDate.of(2020, 11, 11);
        BookingDTO bookingDTO = new BookingDTO(date, 1L, 1L);
        list.getBookingDTOList().add(bookingDTO);
        given(bookingService.getAllValidBookingOfEmployee(any())).willReturn(list);

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/getAllValidBookingOfEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingDTOList[0].date[0]", is(2020)))
                .andExpect(jsonPath("$.bookingDTOList[0].date[1]", is(11)))
                .andExpect(jsonPath("$.bookingDTOList[0].date[2]", is(11)))
                .andExpect(jsonPath("$.bookingDTOList[0].employeeId", is(1)))
                .andExpect(jsonPath("$.bookingDTOList[0].zoneId", is(1)));
        then(bookingService).should(times(1)).getAllValidBookingOfEmployee(any());
    }

    @Disabled
    @Test
    void getAllPastBookingOfEmployee() throws Exception {
        //given
        BookingListDTO list = new BookingListDTO(new ArrayList<>());
        LocalDate date = LocalDate.of(2020, 11, 11);
        BookingDTO bookingDTO = new BookingDTO(date, 1L, 1L);
        list.getBookingDTOList().add(bookingDTO);
        given(bookingService.getAllPastBookingOfEmployee(any())).willReturn(list);

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/getAllPastBookingOfEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingDTOList[0].date[0]", is(2020)))
                .andExpect(jsonPath("$.bookingDTOList[0].date[1]", is(11)))
                .andExpect(jsonPath("$.bookingDTOList[0].date[2]", is(11)))
                .andExpect(jsonPath("$.bookingDTOList[0].employeeId", is(1)))
                .andExpect(jsonPath("$.bookingDTOList[0].zoneId", is(1)));
        then(bookingService).should(times(1)).getAllPastBookingOfEmployee(any());
    }*/

    @Test
    void getAllBookingOfEmployeeInAPeriod() throws Exception {
        //given
        BookingToShowDtoList list = new BookingToShowDtoList(new ArrayList<>());
        LocalDate date = LocalDate.of(2020, 11, 11);
        BookingToShowDTO bookingToshowDTO = new BookingToShowDTO(1L, date, 'A', 1);
        list.getBookingToShowDtoLists().add(bookingToshowDTO);
        given(bookingService.getAllBookingOfEmployeeInAPeriod(any())).willReturn(list);

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/getAllBookingOfEmployeeInAPeriod")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"test\",\"from\":\"2020-11-11\",\"to\":\"2020-11-12\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingToShowDtoLists[0].bookingId", is(1)))
                .andExpect(jsonPath("$.bookingToShowDtoLists[0].date[0]", is(2020)))
                .andExpect(jsonPath("$.bookingToShowDtoLists[0].date[1]", is(11)))
                .andExpect(jsonPath("$.bookingToShowDtoLists[0].date[2]", is(11)))
                .andExpect(jsonPath("$.bookingToShowDtoLists[0].zoneName", is("A")))
                .andExpect(jsonPath("$.bookingToShowDtoLists[0].floor", is(1)));
        then(bookingService).should(times(1)).getAllBookingOfEmployeeInAPeriod(any());
    }

    @Test
    void getAllBookingInAPeriodAdmin() throws Exception {
        //given
        BookingListDTOAdmin list = new BookingListDTOAdmin(new ArrayList<>());
        LocalDate date = LocalDate.of(2020, 11, 11);
        BookingOfEmployeeDTO bookingofEmployeeDTO = new BookingOfEmployeeDTO(1L, date, 'A', 1, "test");
        list.getBookingOfEmployeeDTOList().add(bookingofEmployeeDTO);
        given(bookingService.getAllBookingInAPeriodAdmin(any())).willReturn(list);

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/getAllBookingOfEmployeesInAPeriodAdmin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"2020-11-11\",\"to\":\"2020-11-12\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].bookingId", is(1)))
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].date[0]", is(2020)))
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].date[1]", is(11)))
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].date[2]", is(11)))
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].zoneName", is("A")))
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].floor", is(1)))
                .andExpect(jsonPath("$.bookingOfEmployeeDTOList[0].email", is("test")));
        then(bookingService).should(times(1)).getAllBookingInAPeriodAdmin(any());
    }

    @Test
    void deleteBooking() throws Exception {
        //given
        given(bookingService.deleteOneBookingForEmployee(any())).willReturn(new MessageDTO("test"));

        //when
        mockMvc.perform(post(BookingController.BASE_URL + "/deleteBooking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"bookingId\":\"1\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("test")));
        then(bookingService).should(times(1)).deleteOneBookingForEmployee(any());
    }
}