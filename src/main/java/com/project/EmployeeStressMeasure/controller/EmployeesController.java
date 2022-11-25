package com.project.EmployeeStressMeasure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.model.employees.EmployeesCreationRequest;
import com.project.EmployeeStressMeasure.service.EmployeesService;
import com.project.EmployeeStressMeasure.validator.CheckEmpNoValidator;

import lombok.RequiredArgsConstructor;

@RequestMapping(value = "/api/employees")
@RequiredArgsConstructor
@RestController
public class EmployeesController {
	
	private final EmployeesService employeesService;
	private final CheckEmpNoValidator checkEmpNoValidator;
	
	/* 커스텀 유효성 검증 */
	@InitBinder
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(checkEmpNoValidator);
	}
	
	@GetMapping("/{empNo}/exists")
	public ResponseEntity<Boolean> checkEmpNoDuplicate(@PathVariable String empNo){
		return ResponseEntity.ok(employeesService.checkEmpNoDuplication(empNo));
	}
	
	@PostMapping("/empNoCheck")
	@ResponseBody
	public int empNoCheck(@RequestParam("empNo") String empNo) {
		int cnt = employeesService.empNoCheck(empNo);
		return cnt;
	}
	
	//CRUD
	@PostMapping
	 public ResponseEntity<Employees> createEmployees (@Valid @RequestBody EmployeesCreationRequest request) {
        return ResponseEntity.ok(employeesService.createEmployee(request));
    }
    
	@GetMapping
	public ResponseEntity<Page<Employees>> employeesList (Pageable pageable, String searchKeyword ) {
		if(searchKeyword == null) {
			return ResponseEntity.ok(employeesService.boardList(pageable));
		} else {
			return ResponseEntity.ok(employeesService.boardSearchList(searchKeyword, pageable));
		}
    }
	
	@GetMapping("/{empNo}")
	 public ResponseEntity<Employees> readEmployee (@PathVariable String empNo) {
        return ResponseEntity.ok(employeesService.readEmployee(empNo));
    }
	
	@PutMapping("/{empNo}")
	public ResponseEntity<Employees> updateEmployees (@RequestBody EmployeesCreationRequest request, @PathVariable String empNo) {
        return ResponseEntity.ok(employeesService.updateEmployee(empNo, request));
    }
	
	@DeleteMapping("/{empNo}")
    public ResponseEntity<Void> deleteEmployees (@PathVariable String empNo) {
		employeesService.deleteEmployee(empNo);
        return ResponseEntity.ok().build();
    }
}
