package com.project.EmployeeStressMeasure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.EmployeeStressMeasure.domain.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {
	
	Optional<Employees> findByEmpNo(String empNo);
	Optional<Employees> deleteByEmpNo(String empNo);
	
	//Page<Employees> findByIdx(Employees employees, Pageable pageable);
	//Page<Employees> findByEmpNoContaining(String searchKeyword, Pageable pageable);
	
	@Query(value = "SELECT e FROM Employees e WHERE e.empNo LIKE %:searchKeyword% OR e.name LIKE %:searchKeyword%")
	Page<Employees> findByEmpNoContainingOrNameContaining(@Param("searchKeyword") String searchKeyword, Pageable pageable);
	
	//사번 중복검사
	boolean existsByEmpNo(String empNo);
	int countByEmpNo(String empNo);
}