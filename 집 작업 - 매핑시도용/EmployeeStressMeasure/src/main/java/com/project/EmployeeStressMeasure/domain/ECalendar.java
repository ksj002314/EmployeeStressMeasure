package com.project.EmployeeStressMeasure.domain;


import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
@Getter
public class ECalendar {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ecalendaridx;
	

	private String class_name;
	
	private LocalDateTime start_date;
	
	private LocalDateTime end_date;
	

}
