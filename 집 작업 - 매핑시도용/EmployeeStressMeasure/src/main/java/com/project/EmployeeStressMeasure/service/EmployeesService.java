package com.project.EmployeeStressMeasure.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.domain.Measure;
import com.project.EmployeeStressMeasure.domain.Member;
import com.project.EmployeeStressMeasure.model.employees.EmployeesCreationRequest;
import com.project.EmployeeStressMeasure.model.measure.MeasureCreationRequest;
import com.project.EmployeeStressMeasure.repository.EmployeesRepository;
import com.project.EmployeeStressMeasure.repository.MeasureRepository;
import com.project.EmployeeStressMeasure.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeesService {
	private final EmployeesRepository employeesRepository;
	
	private final MemberRepository memberRepository;
	
	//사번 중복검증
	@Transactional
	public boolean checkEmpNoDuplication(String empNo) {
		boolean empNoDuplicate = employeesRepository.existsByEmpNo(empNo);
		return empNoDuplicate;
	}
	
    @Transactional
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();
 
        /* 유효성 및 중복 검사에 실패한 필드 목록을 받음 */
        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
    
    public int empNoCheck(String empNo) {
    	int cnt = employeesRepository.countByEmpNo(empNo);
		//System.out.println("cnt: " + cnt);
		return cnt;
	}	
	 
	public Employees createEmployee(EmployeesCreationRequest request, String username) {
		
		Member member1 = memberRepository.findByUserid(username);
		
		Employees employees = new Employees();
		employees.setMember(member1);
		BeanUtils.copyProperties(request, employees);
		return employeesRepository.save(employees);
	}
	
	public List<Employees> readEmployees() {
        return employeesRepository.findAll();
    }
	
	// 전체 목록
	public Page<Employees> boardList(Pageable pageable) {
	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "idx"));
	    return employeesRepository.findAll(pageable);
	}
	
	/* 검색 목록(사번)
	public Page<Employees> boardSearchList(String searchKeyword, Pageable pageable) {
	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "idx"));
	    return employeesRepository.findByEmpNoContaining(searchKeyword, pageable);
	}
	*/
	
	//검색 목록(사번 or 사원명)
	public Page<Employees> boardSearchList(String searchKeyword, Pageable pageable) {
	    int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
	    pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "idx"));
	    return employeesRepository.findByEmpNoContainingOrNameContaining(searchKeyword, pageable);
	}
	
	public Employees readEmployee (String empNo) {
        Optional<Employees> employees = employeesRepository.findByEmpNo(empNo);
        if (employees.isPresent()) {
            return employees.get();
        }
        throw new EntityNotFoundException("Cant find any employees under given empNo");
    }

	public Employees updateEmployee (String empNo, EmployeesCreationRequest request) {
		Optional<Employees> optionalEmployees = employeesRepository.findByEmpNo(empNo);
		if (!optionalEmployees.isPresent()) {
		    throw new EntityNotFoundException("employees not present in the database");
		}
		Employees employees = optionalEmployees.get();
		employees.setEmpNo(request.getEmpNo());
		employees.setName(request.getName());
		return employeesRepository.save(employees);
	}
	
	@Transactional 
	public void deleteEmployee (String empNo) {
		 employeesRepository.deleteByEmpNo(empNo);
    }
	


}
