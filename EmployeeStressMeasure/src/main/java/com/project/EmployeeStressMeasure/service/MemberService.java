package com.project.EmployeeStressMeasure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.EmployeeStressMeasure.domain.Member;
import com.project.EmployeeStressMeasure.dto.MemberFormDto;
import com.project.EmployeeStressMeasure.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService  implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	
	
	// 로그인 관련 기능
	@Override
	public Member loadUserByUsername(String userid) throws UsernameNotFoundException {
		return memberRepository.findByUserid(userid)
				.orElseThrow(()-> new UsernameNotFoundException(userid));
	}
	
	 /**
	   * 회원정보 저장
	   *
	   * @param infoDto 회원정보가 들어있는 DTO
	   * @return 저장되는 회원의 PK
	  **/
	
	public Long save(MemberFormDto memberFormDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		memberFormDto.setPassword(encoder.encode(memberFormDto.getPassword()));
		
		return memberRepository.save(Member.builder()
				.userid(memberFormDto.getUserid())
				.address(memberFormDto.getAddress())
				.name(memberFormDto.getName())
				.auth(memberFormDto.getAuth())
				.password(memberFormDto.getPassword()).build()).getIdx();
			
	}
	
	
	
	
}
