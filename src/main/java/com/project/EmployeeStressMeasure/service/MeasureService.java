package com.project.EmployeeStressMeasure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.domain.Measure;
import com.project.EmployeeStressMeasure.model.employees.EmployeesCreationRequest;
import com.project.EmployeeStressMeasure.model.measure.MeasureCreationRequest;
import com.project.EmployeeStressMeasure.repository.EmployeesRepository;
import com.project.EmployeeStressMeasure.repository.MeasureRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MeasureService {
	private final EmployeesRepository employeesRepository;
	private final MeasureRepository measureRepository;
	
	public List<Measure> readMeasures() {
		return measureRepository.findAll();
	}

    public Measure readMeasure(Long id) {
        Optional<Measure> measure = measureRepository.findById(id);
        if (measure.isPresent()) {
            return measure.get();
        }
        throw new EntityNotFoundException("Cant find any measure under given id");
    }
    
    // 전체 목록
  	public Page<Measure> boardList(Pageable pageable) {
  	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
  	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
  	    return measureRepository.findAll(pageable);
  	}
  	
  	/* 검색 목록(사번)
  	public Page<Measure> boardSearchList(String searchKeyword,  Pageable pageable) {
  	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
  	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
  	    return measureRepository.findByEmployees_EmpNoContaining(searchKeyword, pageable);
  	}
  	*/
  	
  	//검색 목록(사번 or 사원명)
  	public Page<Measure> boardSearchList(String searchKeyword, Pageable pageable) {
  	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
  	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
  	    return measureRepository.findByEmployees_EmpNoContainingOrNameContaining(searchKeyword, pageable);
  	}
  	
  	
    public Measure createMeasure (MeasureCreationRequest request) {
        Optional<Employees> employees = employeesRepository.findByEmpNo(request.getEmpNo());
        if (!employees.isPresent()) {
            throw new EntityNotFoundException("employees Not Found");
        }
        
        Measure measureToCreate = new Measure();
        BeanUtils.copyProperties(request, measureToCreate);
        measureToCreate.setEmployees(employees.get());
        return measureRepository.save(measureToCreate);
    }
    
    public Measure updateMeasure(Long id, MeasureCreationRequest request) {
    	Optional<Employees> employees = employeesRepository.findByEmpNo(request.getEmpNo());
        if (!employees.isPresent()) {
            throw new EntityNotFoundException("employees Not Found");
        }
        Optional<Measure> optionalMeasure = measureRepository.findById(id);
        if (!optionalMeasure.isPresent()) {
            throw new EntityNotFoundException("measure Not Found");
        }
        Measure measure = optionalMeasure.get();
        //measure.setCreateDate(request.getCreateDate());
        measure.setStatus(request.getStatus());
        measure.setEmployees(employees.get());
        return measureRepository.save(measure);
    }
    
    public void deleteMeasure(Long id) {
    	measureRepository.deleteById(id);
    }
    

}
