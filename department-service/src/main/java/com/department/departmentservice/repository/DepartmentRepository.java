package com.department.departmentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.department.departmentservice.entity.Department;
//Gets CRUD
public interface DepartmentRepository extends JpaRepository<Department,Long>{

	Department findByDepartmentCode(String deparmentCode);
	
}
