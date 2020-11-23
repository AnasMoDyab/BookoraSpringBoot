package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.services.ZoneService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ZoneController unit test")
@Tag("development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class ZoneControllerTest {

    @Mock
    ZoneService zoneService;

    @InjectMocks
    ZoneController zoneController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(zoneController).build();
    }

    @AfterEach
    void tearDown() {
        reset(zoneService);
    }

    @DisplayName("Get zone list")
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void getZoneList() throws Exception {
        //given
        ZoneListDTO zoneListDTO = new ZoneListDTO(new ArrayList<>());
        ZoneDTO zoneDTO = new ZoneDTO(1l, 1, 'A', true, 10);
        zoneListDTO.getZoneDTOList().add(zoneDTO);
        given(zoneService.getAllZones()).willReturn(zoneListDTO);

        //when
        mockMvc.perform(get(ZoneController.BASE_URL))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zoneDTOList[0].id", is(1)))
                .andExpect(jsonPath("$.zoneDTOList[0].floor", is(1)))
                .andExpect(jsonPath("$.zoneDTOList[0].zone", is("A")))
                .andExpect(jsonPath("$.zoneDTOList[0].activated", is(true)))
                .andExpect(jsonPath("$.zoneDTOList[0].capacity", is(10)));
        then(zoneService).should(times(1)).getAllZones();
    }

    @Test
    void getZoneListByFloor() throws Exception {

        //given
        ZoneListDTO list = new ZoneListDTO(new ArrayList<>());
        //adding two zoneDTO and add the one with larger id, so as to check if the comparator logic work
        ZoneDTO zoneDTO = new ZoneDTO(2L, 2, 'B', true, 20);
        ZoneDTO zoneDTO2 = new ZoneDTO(1L, 1, 'A', true, 10);
        list.getZoneDTOList().add(zoneDTO);
        list.getZoneDTOList().add(zoneDTO2);
        given(zoneService.getZonesByFloor(any())).willReturn(list);

        //when
        mockMvc.perform(get(ZoneController.BASE_URL + "/floor/1"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zoneDTOList[1].id", is(2)))
                .andExpect(jsonPath("$.zoneDTOList[0].id", is(1)))
                .andExpect(jsonPath("$.zoneDTOList[0].floor", is(1)))
                .andExpect(jsonPath("$.zoneDTOList[0].zone", is("A")))
                .andExpect(jsonPath("$.zoneDTOList[0].activated", is(true)))
                .andExpect(jsonPath("$.zoneDTOList[0].capacity", is(10)));
        then(zoneService).should(times(1)).getZonesByFloor(any());
    }

    @DisplayName("Get zone by id")
    @Test
    void getZoneById() throws Exception {
        //given
        ZoneDTO zoneDTO = new ZoneDTO(1l, 1, 'A', true, 10);
        given(zoneService.getZoneById(any())).willReturn(zoneDTO);

        //when
        mockMvc.perform(get(ZoneController.BASE_URL + "/zone/{id}", zoneDTO.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.floor", is(1)))
                .andExpect(jsonPath("$.zone", is("A")))
                .andExpect(jsonPath("$.activated", is(true)))
                .andExpect(jsonPath("$.capacity", is(10)));
        then(zoneService).should(times(1)).getZoneById(any());
    }

    @DisplayName("Check status of a zone on a day")
    @Test
    void checkStatusOfAZoneOnADay() throws Exception {
        //given
        StatusOfAZoneOnADayDTO statusOfAZoneOnADayDTO = new StatusOfAZoneOnADayDTO(10, 20);
        given(zoneService.checkStatusOfAZoneOnADay(any())).willReturn(statusOfAZoneOnADayDTO);

        //when
        mockMvc.perform(post(ZoneController.BASE_URL + "/checkStatusOfAZoneOnADay")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"zoneId\":\"1\",\"date\":\"2020-11-12\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalReservation", is(10)))
                .andExpect(jsonPath("$.capacity", is(20)))
                .andExpect(jsonPath("$.bookedPercentage", is(50)));
        then(zoneService).should(times(1)).checkStatusOfAZoneOnADay(any());
    }


    @Test
    void checkStatusOfAllZoneInAFloor() throws Exception {
        //given
        List<StatusOfAZoneOnADayDTO> list = new ArrayList<>();
        list.add(new StatusOfAZoneOnADayDTO(10, 20));
        given(zoneService.checkStatusOfAllZoneInAFloor(any())).willReturn(list);

        //when
        mockMvc.perform(post(ZoneController.BASE_URL + "/checkStatusOfAllZoneInAFloor")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"floorId\":\"1\",\"date\":\"2020-11-12\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].totalReservation", is(10)))
                .andExpect(jsonPath("$[0].capacity", is(20)))
                .andExpect(jsonPath("$[0].bookedPercentage", is(50)));
        then(zoneService).should(times(1)).checkStatusOfAllZoneInAFloor(any());
    }

    @Test
    void zoneSettings() throws Exception {
        //given
        MessageDTO messageDTO = new MessageDTO("test");
        given(zoneService.zoneSettings(any())).willReturn(messageDTO);

        //when
        mockMvc.perform(post(ZoneController.BASE_URL + "/ZoneSettings")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"floor\":\"1\",\"zoneId\":\"1\",\"capacity\":\"10\",\"activated\":\"true\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("test")));
        then(zoneService).should(times(1)).zoneSettings(any());
    }

    @Test
    void checkStatusOfAllFloorPeriod() throws Exception {
        //given
        List<FloorStatusPeriodDTO> list = new ArrayList<>();
        list.add(new FloorStatusPeriodDTO(1, 30));
        given(zoneService.checkStatusOfAllFloorPeriod(any())).willReturn(list);

        //when
        mockMvc.perform(post(ZoneController.BASE_URL + "/CheckStatusOfAllFloorPeriod")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"2020-11-12\",\"to\":\"2020-11-12\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].floor", is(1)))
                .andExpect(jsonPath("$[0].totalBooking", is(30)));
        then(zoneService).should(times(1)).checkStatusOfAllFloorPeriod(any());
    }

    @Test
    void checkStatusOfBuildingOnPeriod() throws Exception {
        //given
        TotalBookingInBuildingDTO totalBookingInBuildingDTO = new TotalBookingInBuildingDTO(10);
        given(zoneService.CheckStatusOfTheBuildingOnPeriod(any())).willReturn(totalBookingInBuildingDTO);

        //when
        mockMvc.perform(post(ZoneController.BASE_URL + "/CheckStatusOfBuildingOnPeriod")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"2020-11-12\",\"to\":\"2020-11-12\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalForAllFloor", is(10)));
        then(zoneService).should(times(1)).CheckStatusOfTheBuildingOnPeriod(any());
    }
}