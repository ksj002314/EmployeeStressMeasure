package com.project.EmployeeStressMeasure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.EmployeeStressMeasure.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Optional<Member> findByUserid(String userid);		// userid를 통해 회원 조회
}
