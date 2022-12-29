package com.project.EmployeeStressMeasure.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.EmployeeStressMeasure.dto.MemberFormDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_idx")
	private Long idx;
	
	@Column(unique = true)
	private String userid;
	
	private String name;
	
	private String password;
	
	private String address;
	
	private String auth;
	
	@Builder
	public Member(String userid, String name, String password, String address, String auth) {
		this.userid = userid;
		this.name = name;
		this.password = password;
		this.address = address;
		this.auth = auth;
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    Set<GrantedAuthority> roles = new HashSet<>();
	    for (String role : auth.split(",")) {
	      roles.add(new SimpleGrantedAuthority(role));
	    }
	    return roles;
	  }

	@Override
	public String getUsername() {
		return userid;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	

	// 계정 만료 여부 반환 
	@Override
	public boolean isAccountNonExpired() {
		return true;	 // 만료되었는지 확인하는 로직	  true => 만료되지 않았음
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;	 // 계정 잠금되었는지 확인하는 로직	true => 잠금되지 않았음
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;	 // 패스워드가 만료되었는지 확인하는 로직		true => 만료되지 않았음
	}

	@Override
	public boolean isEnabled() {
		return true;	// 계정이 사용 가능한지 확인하는 로직		true => 사용 가능
	}

	
}
