package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;
import com.tietoevry.bookorabackend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

/**
 *Mapper maps between Employee entity and DTOs.
 */

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    /**
     * Maps between EmployeeDTO and Employee entity.
     * @param employee
     * @return A EmployeeDTO that contains Employee info.
     */

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

}

