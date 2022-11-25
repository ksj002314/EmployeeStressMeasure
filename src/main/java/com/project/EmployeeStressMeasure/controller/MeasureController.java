package com.project.EmployeeStressMeasure.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.domain.Measure;
import com.project.EmployeeStressMeasure.model.employees.EmployeesCreationRequest;
import com.project.EmployeeStressMeasure.model.measure.MeasureCreationRequest;
import com.project.EmployeeStressMeasure.repository.EmployeesRepository;
import com.project.EmployeeStressMeasure.service.EmployeesService;
import com.project.EmployeeStressMeasure.service.MeasureService;

import lombok.RequiredArgsConstructor;


@RestController 
@RequiredArgsConstructor
@RequestMapping(value = "/api/measure")
public class MeasureController {
	
	private final MeasureService measureService;
	
	//CRUD
	@PostMapping
	public ResponseEntity<Measure> createMeasure (@RequestBody MeasureCreationRequest request) {
	    return ResponseEntity.ok(measureService.createMeasure(request));
	}
	
	@GetMapping
	public ResponseEntity<Page<Measure>> measureList (Pageable pageable, String searchKeyword) {
		if(searchKeyword == null) {
			return ResponseEntity.ok(measureService.boardList(pageable));
		} else {
			return ResponseEntity.ok(measureService.boardSearchList(searchKeyword, pageable));
		}
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Measure> readMeasure (@PathVariable Long id) {
	    return ResponseEntity.ok(measureService.readMeasure(id));
	}
	
	@PutMapping("/{id}")
	 public ResponseEntity<Measure> updateMeasure (@PathVariable("id") Long id, @RequestBody MeasureCreationRequest request) {
        return ResponseEntity.ok(measureService.updateMeasure(id, request));
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMeasure (@PathVariable Long id) {
		measureService.deleteMeasure(id);
        return ResponseEntity.ok().build();
    }
	
	
}

  
	 
