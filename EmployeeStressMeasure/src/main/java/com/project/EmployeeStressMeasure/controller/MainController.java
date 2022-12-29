package com.project.EmployeeStressMeasure.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.EmployeeStressMeasure.domain.Employees;
import com.project.EmployeeStressMeasure.domain.Measure;
import com.project.EmployeeStressMeasure.service.EmployeesService;
import com.project.EmployeeStressMeasure.service.MeasureService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
//@RequestMapping(value = "/employeeStress")
public class MainController {
	
	private final EmployeesService employeesService;
	private final MeasureService measureService;
	
	@GetMapping("/")
    public String root() {
        return "forward:/measure";
    }
    
	
	//근로자 목록조회
	@GetMapping("/employees")
	public String employeesList(@PageableDefault Pageable pageable, String searchKeyword, Model model) {
		Page<Employees> employees = null;
		if(searchKeyword == null) {
			employees = employeesService.boardList(pageable);
		} else {
			employees = employeesService.boardSearchList(searchKeyword, pageable);
		}
			model.addAttribute("employees", employees);
			model.addAttribute("pageable", pageable);
		
        return "/employees/list";
    }
	
	//근로자 상세보기
	@GetMapping("/employees/view")
	public String employeesView(Model model, String empNo) {
		model.addAttribute("employees", employeesService.readEmployee(empNo));
		
        return "/employees/view";
    }
	
	//근로자 업데이트
	@GetMapping("/employees/update")
	public String employeesUpdate(Model model, String empNo) {
		model.addAttribute("employees", employeesService.readEmployee(empNo));
        return "/employees/update";
    }
	
	
	
	//측정 목록조회
	@GetMapping("/measure")
	public String measureList(@PageableDefault Pageable pageable, String searchKeyword, Model model) {
		Page<Measure> measure = null;
		if(searchKeyword == null) {
			measure = measureService.boardList(pageable);
		} else {
			measure = measureService.boardSearchList(searchKeyword, pageable);
		}
			model.addAttribute("measure", measure);
		
        return "/measure/list";
    }
	
	//측정 상세보기
	@GetMapping("/measure/view")
	public String measureView(Model model, Long id) {
		model.addAttribute("measure", measureService.readMeasure(id));
        return "/measure/view";
    }
	
	//측정 업데이트
	@GetMapping("/measure/update")
	public String measureUpdate(Model model, Long id) {
		model.addAttribute("measure", measureService.readMeasure(id));
        return "/measure/update";
    }
	
	
	
	// 스케줄 관리
	@GetMapping("/schedule")
	public String schedule() {
		return "/schedule/schedule";
	}
	
	
}
