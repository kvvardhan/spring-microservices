package com.employee.employeeservice.service;

import com.employee.employeeservice.dto.APIResponseDto;
import com.employee.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
	
	EmployeeDto saveEmployee(EmployeeDto employeeDto);

	APIResponseDto getEmployeeById(Long employeeId);
}
