package com.project.EmployeeStressMeasure.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.project.EmployeeStressMeasure.model.employees.EmployeesCreationRequest;
import com.project.EmployeeStressMeasure.repository.EmployeesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckEmpNoValidator extends AbstractValidator<EmployeesCreationRequest>{
	
	private final EmployeesRepository employeesRepository;

	@Override
	protected void doValidate(EmployeesCreationRequest request, Errors errors) {
		if (employeesRepository.existsByEmpNo(request.getEmpNo())) {
			/* 중복인 경우 */
			errors.rejectValue("empNo","사번 중복 오류", "이미 사용 중인 사번입니다.");
		}	
	}
}