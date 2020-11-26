package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.SignUpDTO;
import com.tietoevry.bookorabackend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper maps between SignUPDTO and Employee entity.
 */
@Mapper
public interface SignUpMapper {

    SignUpMapper INSTANCE = Mappers.getMapper(SignUpMapper.class);

    /**
     * Maps between SignUPDTO and Employee entity.
     *
     * @param signUpDTO
     * @return A employee
     */
    @Mapping(target = "roles", ignore = true)
    Employee signUpDTOtoEmployee(SignUpDTO signUpDTO);
}
