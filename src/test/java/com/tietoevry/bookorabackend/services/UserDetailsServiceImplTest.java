package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Role;
import com.tietoevry.bookorabackend.model.RoleEnum;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

@DisplayName("ZoneServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    UserDetailsImpl userDetails;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @AfterEach
    void tearDown() {
        reset(employeeRepository);
        reset(userDetails);
    }

    @DisplayName("Load a non-existing user")
    @Test
    void loadUserAnonExistingUser() {
        //given
        String testEmail = "test";
        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);

        //when then
        assertThatThrownBy(() -> {
            userDetailsService.loadUserByUsername(testEmail);
        }).isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("User Not Found with username: " + testEmail);
    }

    @DisplayName("Load a user")
    @Test
    void loadUserAUser() {
        //given
        String testEmail = "test";
        Employee employee = new Employee();
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleEnum.ROLE_ADMIN));
        employee.setRoles(roles);
        employee.setEmail(testEmail);
        UserDetailsImpl userDetails = new UserDetailsImpl(null,testEmail,null,null);
        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);

        //when
        UserDetails userDetails1 = userDetailsService.loadUserByUsername(any());

        //then
        assertThat(userDetails1.getUsername()).isEqualTo("test");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
    }
}