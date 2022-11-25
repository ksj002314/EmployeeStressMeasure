package com.project.EmployeeStressMeasure.domain;

import java.util.List;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Getter
@Entity
@Table(name = "measure")
public class Measure extends BaseTimeEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="measure_no")
	private Long id;
	
	@Setter @Column(nullable = false) 
	private int status;
	
	@Setter
	@ManyToOne
    @JoinColumn(name = "emp_no")
    @JsonManagedReference
    private Employees employees;
	
}
