package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.SignUpDTO;
import com.tietoevry.bookorabackend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that maps between SignUPDTO and Employee entity.
 */
@Mapper
public interface SignUpMapper {

    SignUpMapper INSTANCE = Mappers.getMapper(SignUpMapper.class);

    /**
     * Maps SignUPDTO to Employee entity.
     *
     * @param signUpDTO A signUpDTO that contains information about the registering employee
     * @return A Employee object
     */
    @Mapping(target = "roles", ignore = true)
    Employee signUpDTOtoEmployee(SignUpDTO signUpDTO);
}
