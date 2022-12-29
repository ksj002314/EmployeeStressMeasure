package com.project.EmployeeStressMeasure.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "employees")
public class Employees {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name="idx")
	private Long idx;
	
	@Setter @Column(nullable = false, name="emp_no") 
	private String empNo;
	
	@Setter @Column(nullable = false, name="name")
	private String name;
	
	@JsonBackReference
	@OneToMany(mappedBy = "employees",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Measure> measures = new ArrayList<>();
	
}
