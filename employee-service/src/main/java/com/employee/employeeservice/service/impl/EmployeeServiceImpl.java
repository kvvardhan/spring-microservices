package com.employee.employeeservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.employee.employeeservice.dto.APIResponseDto;
import com.employee.employeeservice.dto.DepartmentDto;
import com.employee.employeeservice.dto.EmployeeDto;
import com.employee.employeeservice.entity.Employee;
import com.employee.employeeservice.repository.EmployeeRepository;
import com.employee.employeeservice.service.APIClient;
import com.employee.employeeservice.service.EmployeeService;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private EmployeeRepository employeeRepository;
	
	//private RestTemplate restTemplate;
	private WebClient webClient;
	
//	private APIClient apiClient;
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		// TODO Auto-generated method stub
		
		
		Employee employee = new Employee(
				employeeDto.getId(),
				employeeDto.getFirstName(),
				employeeDto.getLastName(),
				employeeDto.getEmail(),
				employeeDto.getDepartmentCode()
				);
		Employee savedEmployee = employeeRepository.save(employee);
		
		EmployeeDto savedEmployeeDto = new EmployeeDto(
				savedEmployee.getId(),
				savedEmployee.getFirstName(),
				savedEmployee.getLastName(),
				savedEmployee.getEmail(),
				savedEmployee.getDepartmentCode()
				);
		
		return savedEmployeeDto;
	}

//	@CircuitBreaker(name = "${spring.application.name}" , fallbackMethod = "getDefaultDepartment")
	@Retry(name = "${spring.application.name}" , fallbackMethod = "getDefaultDepartment")
	@Override
	public APIResponseDto getEmployeeById(Long employeeId) {
		// TODO Auto-generated method stub
		
		LOGGER.info("inside getEmployeeById() method");
		Employee employee = employeeRepository.findById(employeeId).get();
		
//		ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDto.class);
//		
//		DepartmentDto departmentDto = responseEntity.getBody();
		
		DepartmentDto departmentDto = webClient.get().uri("http://localhost:8082/api/departments/" +employee.getDepartmentCode()).retrieve().bodyToMono(DepartmentDto.class).block();
		
		// Will enable again if required
//		DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());
		
		EmployeeDto employeeDto = new EmployeeDto(
				employee.getId(),
				employee.getFirstName(),
				employee.getLastName(),
				employee.getEmail(),
				employee.getDepartmentCode()
				);
		
		APIResponseDto apiResponseDto = new APIResponseDto();
		apiResponseDto.setEmployee(employeeDto);
		apiResponseDto.setDepartment(departmentDto);
		
		return apiResponseDto;
		
	}
		
		public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
			
			LOGGER.info("inside getDefaultDepartment() method");
			Employee employee = employeeRepository.findById(employeeId).get();
			DepartmentDto departmentDto = new DepartmentDto();
			departmentDto.setDepartmentName("R&D Department");
			departmentDto.setDepartmentCode("RD001");
			departmentDto.setDepartmentDescription("Research and Development Department");
			
			// Will enable again if required
//			DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmentCode());
			
			EmployeeDto employeeDto = new EmployeeDto(
					employee.getId(),
					employee.getFirstName(),
					employee.getLastName(),
					employee.getEmail(),
					employee.getDepartmentCode()
					);
			
			APIResponseDto apiResponseDto = new APIResponseDto();
			apiResponseDto.setEmployee(employeeDto);
			apiResponseDto.setDepartment(departmentDto);
			
			return apiResponseDto;
			
		}
	}


