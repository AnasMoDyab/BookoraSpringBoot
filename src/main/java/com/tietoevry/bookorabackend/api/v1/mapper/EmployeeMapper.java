package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.EmployeeDTO;
import com.tietoevry.bookorabackend.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that maps between Employee entity and DTOs.
 */

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    /**
     * Maps Employee to EmployeeDTO.
     *
     * @param employee A Employee object
     * @return A EmployeeDTO that contains Employee info.
     */

    EmployeeDTO employeeToEmployeeDTO(Employee employee);

}

