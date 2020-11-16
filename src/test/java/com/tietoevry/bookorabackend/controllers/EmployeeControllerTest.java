package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.EmployeeListDTO;
import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
import com.tietoevry.bookorabackend.services.EmployeeService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("EmployeeController unit test")
@Tag("development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    EmployeeService employeeService;

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeController employeeController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @AfterEach
    void tearDown() {
        reset(employeeService);
        reset(confirmationTokenService);
        reset(employeeRepository);
    }

    @Test
    void getEmployeeList() throws Exception {
        //given
        EmployeeListDTO employeeListDTO = new EmployeeListDTO(new ArrayList<>());
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "root", "Hi", "root@tietoevry.com",
                "123456aB@", new HashSet<String>(), "test");
        employeeListDTO.getEmployeeDTOList().add(employeeDTO);
        given(employeeService.getAllEmployees()).willReturn(employeeListDTO);

        //when
        mockMvc.perform(get(EmployeeController.BASE_URL))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeDTOList[0].id", is(1)))
                .andExpect(jsonPath("$.employeeDTOList[0].firstName", is("root")))
                .andExpect(jsonPath("$.employeeDTOList[0].lastName", is("Hi")))
                .andExpect(jsonPath("$.employeeDTOList[0].email", is("root@tietoevry.com")))
                .andExpect(jsonPath("$.employeeDTOList[0].password", is("123456aB@")))
                .andExpect(jsonPath("$.employeeDTOList[0].employee_url", is("test")));
        then(employeeService).should(times(1)).getAllEmployees();
    }

    @Test
    void getEmployeeById() throws Exception {
        //given
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "root", "Hi", "root@tietoevry.com",
                "123456aB@", new HashSet<String>(), "test");
        given(employeeService.getEmployeeById(any())).willReturn(employeeDTO);

        //when
        mockMvc.perform(get(EmployeeController.BASE_URL+"/"+employeeDTO.getId()))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("root")))
                .andExpect(jsonPath("$.lastName", is("Hi")))
                .andExpect(jsonPath("$.email", is("root@tietoevry.com")))
                .andExpect(jsonPath("$.password", is("123456aB@")))
                .andExpect(jsonPath("$.employee_url", is("test")));
        then(employeeService).should(times(1)).getEmployeeById(anyLong());
    }

    @Test
    void getEmployeeByEmail() throws Exception {
        //given
        EmployeeDTO employeeDTO = new EmployeeDTO(1L, "root", "Hi", "root@tietoevry.com",
                "123456aB@", new HashSet<String>(), "test");
        given(employeeService.getEmployeeByEmail(any())).willReturn(employeeDTO);

        //when
        mockMvc.perform(get(EmployeeController.BASE_URL+"/email/{email}", "test" ))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("root")))
                .andExpect(jsonPath("$.lastName", is("Hi")))
                .andExpect(jsonPath("$.email", is("root@tietoevry.com")))
                .andExpect(jsonPath("$.password", is("123456aB@")))
                .andExpect(jsonPath("$.employee_url", is("test")));
        then(employeeService).should(times(1)).getEmployeeByEmail(any());


    }

    @Test
    void createNewEmployee() throws Exception {
        //given
        given(employeeService.createNewEmployee(any())).willReturn(new MessageDTO("test"));

        //when
        mockMvc.perform(post(EmployeeController.BASE_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lastName\":\"root\",\"firstName\":\"Hi\",\"email\":\"root@tietoevry.com\",\"password\":\"123456aB@\",\"roles\":[\"user\"]}"))
                //then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("test")));
        then(employeeService).should(times(1)).createNewEmployee(any());
    }

    @Test
    void authenticateUser() throws Exception {
        //given
        List<String> roles = new ArrayList<>();
        roles.add("user");
        JwtDTO jwtDTO = new JwtDTO("123",1L,"root@tietoevry.com",roles);
        given(employeeService.logIn(any())).willReturn(jwtDTO);

        //when
        mockMvc.perform(post(EmployeeController.BASE_URL + "/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"root@tietoevry.com\",\"password\":\"123456aB@\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("123")))
                .andExpect(jsonPath("$.type", is("Bearer")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("root@tietoevry.com")))
                .andExpect(jsonPath("$.roles[0]", is("user")));
        then(employeeService).should(times(1)).logIn(any());
    }

    @Test
    void updateEmployee() throws Exception {
        //given
       given(employeeService.updateEmployee(any())).willReturn(new MessageDTO("test"));

        //when
        mockMvc.perform(post(EmployeeController.BASE_URL + "/updateEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"root@tietoevry.com\",\"roles\":[\"user\"]}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("test")));
        then(employeeService).should(times(1)).updateEmployee(any());

    }

    @Test
    void deleteEmployee() throws Exception {

        //when
        mockMvc.perform(delete(EmployeeController.BASE_URL + "/1"))
                //then
                .andExpect(status().isOk());
        then(employeeService).should(times(1)).deleteEmployeeDTO(anyLong());
    }

    @Test
    void forgotUserPassword() throws Exception {
        //given
        given(employeeService.sendForgetPasswordCode(any())).willReturn(new MessageDTO("test"));

        //when
        mockMvc.perform(post(EmployeeController.BASE_URL + "/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"root@tietoevry.com\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("test")));
        then(employeeService).should(times(1)).sendForgetPasswordCode(any());
    }

    @Test
    void resetUserPassword() throws Exception {
        //given
        given(employeeService.resendConfirmationToken(any())).willReturn(new MessageDTO("test"));

        //when
        mockMvc.perform(post(EmployeeController.BASE_URL + "/reactive-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"root@tietoevry.com\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("test")));
        then(employeeService).should(times(1)).resendConfirmationToken(any());
    }
}
