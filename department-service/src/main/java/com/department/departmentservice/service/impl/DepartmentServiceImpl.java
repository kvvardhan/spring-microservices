package com.department.departmentservice.service.impl;

import org.springframework.stereotype.Service;

import com.department.departmentservice.dto.DepartmentDto;
import com.department.departmentservice.entity.Department;
import com.department.departmentservice.repository.DepartmentRepository;
import com.department.departmentservice.service.DepartmentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentRepository departmentrepository;
	
	
	


	@Override
	public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
		// TODO Auto-generated method stub
		// Convert department dto to department jpa entity
		Department department = new Department(
				departmentDto.getId(),
				departmentDto.getDepartmentName(),
				departmentDto.getDepartmentDescription(),
				departmentDto.getDepartmentCode()
				);
		
		Department savedDepartment = departmentrepository.save(department);
		
		
		DepartmentDto savedDepartmentDto = new DepartmentDto(
				savedDepartment.getId(),
				savedDepartment.getDepartmentName(),
				savedDepartment.getDepartmentDescription(),
				savedDepartment.getDepartmentCode()
				);
		return savedDepartmentDto;
	}





	@Override
	public DepartmentDto getDepartmentByCode(String departmentCode) {
		// TODO Auto-generated method stub
		
		Department department = departmentrepository.findByDepartmentCode(departmentCode);
		
		DepartmentDto departmentDto = new DepartmentDto(
				department.getId(),
				department.getDepartmentName(),
				department.getDepartmentDescription(),
				department.getDepartmentCode()
				);
		return departmentDto;
	}

}
