package com.project.EmployeeStressMeasure.domain;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(name = "measure")
public class Measure extends BaseTimeEntity{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="measure_no")
	private Long id;
	
	@Setter @Column(nullable = false) 
	private String status;
	
	@Setter
	@ManyToOne
    @JoinColumn(name = "emp_no")
    @JsonManagedReference
    private Employees employees;
	
	
	// 파일 등록 변수
	@Setter
	private String filename;
	@Setter
	private String url;
	@Setter
	private Long size;
	

}
