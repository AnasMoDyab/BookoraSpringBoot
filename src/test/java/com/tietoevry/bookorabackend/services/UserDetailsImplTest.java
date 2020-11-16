package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Role;
import com.tietoevry.bookorabackend.model.RoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UserDetailsImp unit test")
@Tag("Development")
@Tag("UnitTest")
class UserDetailsImplTest {

    @Test
    void buildAUser() {

        //given
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleEnum.ROLE_ADMIN));
        Employee employee = new Employee("Per", "John", "email", "pass");
        employee.setId(1L);
        employee.setRoles(roles);
        UserDetailsImpl userDetails2 = UserDetailsImpl.build(employee);

        //when
        UserDetailsImpl userDetails1 = UserDetailsImpl.build(employee);

        //then
        assertThat(userDetails1.getUsername()).isEqualTo("email");
        assertThat(userDetails1.getPassword()).isEqualTo("pass");
        assertThat(userDetails1.getAuthorities()).isNotEmpty();
        assertThat(userDetails1.getId()).isEqualTo(1);
        assertThat(userDetails1.getEmail()).isEqualTo("email");
        assertThat(userDetails1.isAccountNonExpired()).isEqualTo(true);
        assertThat(userDetails1.isAccountNonLocked()).isEqualTo(true);
        assertThat(userDetails1.isCredentialsNonExpired()).isEqualTo(true);
        assertThat(userDetails1.isEnabled()).isEqualTo(true);
        assertThat(userDetails1.equals(userDetails1)).isEqualTo(true);
        assertThat(userDetails1.equals(null)).isEqualTo(false);
        assertThat(userDetails1.equals(new Object())).isEqualTo(false);
        assertThat(userDetails1.equals(userDetails2)).isEqualTo(true);
    }


}