package com.project.EmployeeStressMeasure.model.employees;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EmployeesCreationRequest {
	
	@NotBlank(message = "사번은 필수 입력 값입니다")
	private String empNo;
	
	@NotBlank(message = "사원명은 필수 입력 값입니다")
	private String name;
}
