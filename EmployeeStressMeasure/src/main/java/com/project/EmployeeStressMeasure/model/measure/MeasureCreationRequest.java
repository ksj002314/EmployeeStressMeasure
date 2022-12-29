package com.project.EmployeeStressMeasure.model.measure;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.project.EmployeeStressMeasure.domain.BaseTimeEntity;
import com.project.EmployeeStressMeasure.domain.Employees;

import lombok.Data;
import lombok.Setter;

@Data
public class MeasureCreationRequest extends BaseTimeEntity{
	private String status;
	private String empNo;
}
