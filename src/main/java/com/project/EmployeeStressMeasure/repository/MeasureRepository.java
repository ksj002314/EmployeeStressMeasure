package com.project.EmployeeStressMeasure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.domain.Measure;

public interface MeasureRepository extends JpaRepository<Measure, Long> {
	//Page<Measure> findById(Measure measure, Pageable pageable);
	//Page<Measure> findByEmployees_EmpNoContaining(String searchKeyword, Pageable pageable);
	
	@Query(value = "SELECT m FROM Measure m JOIN m.employees e WHERE e.empNo LIKE %:searchKeyword% OR e.name LIKE %:searchKeyword%")
	Page<Measure> findByEmployees_EmpNoContainingOrNameContaining(@Param("searchKeyword") String searchKeyword, Pageable pageable);
}
